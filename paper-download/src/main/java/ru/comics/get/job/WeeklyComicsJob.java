package ru.comics.get.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.comics.get.ComicsMetaUtil;
import ru.comics.get.entity.Issue;
import ru.comics.get.entity.Series;
import ru.comics.get.repository.IssueRepository;
import ru.comics.get.repository.SeriesRepository;
import ru.comics.get.service.WeeklyComicsService;

import java.time.LocalDate;

@Component
@Slf4j
public class WeeklyComicsJob {

    private final SeriesRepository seriesRepository;
    private final IssueRepository issueRepository;
    private final WeeklyComicsService weeklyComicsService;

    public WeeklyComicsJob(
            SeriesRepository seriesRepository,
            IssueRepository issueRepository,
            WeeklyComicsService weeklyComicsService
    ) {
        this.seriesRepository = seriesRepository;
        this.issueRepository = issueRepository;
        this.weeklyComicsService = weeklyComicsService;
    }

//    @Scheduled(cron = "0 */5 * ? * *")
    public void checkWeekly() {
        var weeklyComicsList = ComicsMetaUtil.getWeeklyComicsList(LocalDate.now());
        for (var weeklyComics : weeklyComicsList) {
            var series = seriesRepository.findAllByNameAndYear(weeklyComics.getTitle(), weeklyComics.getPublisher(), weeklyComics.getPublicationDate().getYear());
            Series currentSeries;
            if (series.isEmpty()) {
                var newSeries = Series.builder()
                        .name(weeklyComics.getTitle())
                        .startYear(weeklyComics.getPublicationDate().getYear())
                        .endYear(weeklyComics.getPublicationDate().getYear())
                        .publisher(weeklyComics.getPublisher())
                        .build();

                currentSeries = seriesRepository.save(newSeries);

            } else {
                currentSeries = series.stream().findFirst().get();
                if (currentSeries.getEndYear() < weeklyComics.getPublicationDate().getYear()) {
                    currentSeries.setEndYear(weeklyComics.getPublicationDate().getYear());
                    currentSeries = seriesRepository.save(currentSeries);
                }
            }

            var issueWrapper = issueRepository.findFirstBySeriesAndAndNumber(currentSeries, weeklyComics.getNumber());
            if (issueWrapper.isEmpty()) {
                var issue = Issue.builder()
                        .number(weeklyComics.getNumber())
                        .publicationDate(weeklyComics.getPublicationDate())
                        .series(currentSeries)
                        .build();

                issueRepository.save(issue);
            }
        }
    }

//    @Scheduled(cron = "0 */5 * ? * *")
    public void downloadSubscription() {
        log.info("Download issues start");
        weeklyComicsService.downloadSubscribedIssues();
        log.info("Download issues end");
    }
}
