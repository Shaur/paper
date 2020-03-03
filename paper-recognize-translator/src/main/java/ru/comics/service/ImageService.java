package ru.comics.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Slf4j
@Service
public class ImageService {

    private final PreprocessingService preprocessingService;
    private final OcrProcessingService ocrProcessingService;
    private final TranslationService translateService;

    private final File uploadDir;

    @Autowired
    public ImageService(PreprocessingService preprocessingService,
                        OcrProcessingService ocrProcessingService,
                        TranslationService translateService,
                        @Value("${upload.path}") String uploadPath
    ) {
        this.preprocessingService = preprocessingService;
        this.ocrProcessingService = ocrProcessingService;
        this.translateService = translateService;
        this.uploadDir = new File(uploadPath);

        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            log.warn("Can not create upload dir");
        }
    }

    public Path storeFile(FilePart multipartFile) {
        String originalFilename = multipartFile.filename();

        Path pathToFile = uploadDir.toPath().resolve(originalFilename);
        multipartFile.transferTo(pathToFile);

        return pathToFile;
    }
    
    @Async
    public void processFile(Path path) {
        
    }
    
}
