package ru.comics.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.comics.ocr.service.OcrProcessingService;
import ru.comics.translater.repository.EnRuWordRepository;
import ru.comics.translater.transfer.Translate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ImageProcessingService {

    private final File uploadDir;
    private final OcrProcessingService ocrProcessingService;
    private final EnRuWordRepository enRuWordRepository;

    public ImageProcessingService(@Value("${upload.path}") String uploadPath,
                                  OcrProcessingService ocrProcessingService,
                                  EnRuWordRepository enRuWordRepository) {
        this.uploadDir = new File(uploadPath);
        this.ocrProcessingService = ocrProcessingService;
        this.enRuWordRepository = enRuWordRepository;

        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            log.warn("Can not create upload dir");
        }
    }
    
    public List<Translate> process(MultipartFile multipartFile) {
        Path storeFile = storeFile(multipartFile);
        if(storeFile != null) {
            var words = recogniseFile(storeFile);
            return enRuWordRepository.findAllByWordIn(words);
        }
        
        return Collections.emptyList();
    }

    private Collection<String> recogniseFile(Path filePath) {
        return ocrProcessingService.process(filePath);
    }

    private Path storeFile(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        Path pathToFile = uploadDir.toPath().resolve(originalFilename);
        try {
            Files.copy(multipartFile.getInputStream(), pathToFile, StandardCopyOption.REPLACE_EXISTING);
            return pathToFile;
        } catch (IOException ex) {
            log.warn("File {} can not be saved. Reason: {}", originalFilename, ex);
        }
        
        return null;
    }
}
