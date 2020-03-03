package ru.comics.get.service;

import com.github.junrar.Junrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.comics.get.SearchIssueFileRequest;
import ru.comics.get.entity.Issue;
import ru.comics.get.repository.IssueRepository;
import ru.comics.get.system.PropertyService;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Service
public class WeeklyComicsService {

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(4);
    private static final List<Long> DOWNLOAD_QUEUE = new ArrayList<>();

    private final PropertyService propertyService;
    private final IssueRepository issueRepository;

    public WeeklyComicsService(PropertyService propertyService, IssueRepository issueRepository) {
        this.propertyService = propertyService;
        this.issueRepository = issueRepository;
    }

    public void downloadSubscribedIssues() {
        issueRepository.getSubscribedIssues().stream()
                .filter(issue -> !DOWNLOAD_QUEUE.contains(issue.getId()))
                .peek(issue -> DOWNLOAD_QUEUE.add(issue.getId()))
                .map(DownloadTask::new)
                .forEach(THREAD_POOL::execute);
    }

    private class DownloadTask implements Runnable {
        private final Issue issue;

        public DownloadTask(Issue issue) {
            this.issue = issue;
        }

        @Override
        @Transactional
        public void run() {
            var title = issue.getSeries().getName();
            var number = issue.getNumber();

            var inputStream = (new SearchIssueFileRequest(title, number)).execute();
            if (inputStream == null)
                return;

            var savePath = propertyService.createSavePath(issue.getSeries().getId(), issue.getId());
            try {
                Files.copy(inputStream, savePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            try(var fis = new FileInputStream(savePath.toFile())) {
                Junrar.extract(fis, savePath.getParent().toFile());
                Files.list(savePath.getParent())
                        .map(Path::toFile)
                        .filter(f -> !(f.getName().endsWith(".jpg") || f.getName().endsWith(".png")))
                        .forEach(File::delete);
            } catch (Exception ex) {
                log.error("Error while unrar archive. Reason: ", ex);
                return;
            }

            issue.setDownloaded(true);
            issueRepository.save(issue);
            DOWNLOAD_QUEUE.remove(issue.getId());
        }
    }
}
