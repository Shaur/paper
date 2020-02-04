package ru.comics.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.comics.ocr.service.OcrProcessingService;
import ru.comics.ocr.service.PreprocessingService;
import ru.comics.translater.transfer.Translate;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

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

    private Path storeFile(FilePart multipartFile) {
        String originalFilename = multipartFile.filename();

        Path pathToFile = uploadDir.toPath().resolve(originalFilename);
        multipartFile.transferTo(pathToFile);

        return pathToFile;
    }

    public Flux<Translate> processImage(Mono<FilePart> file) {
        return file
                .map(this::storeFile)
                .map(preprocessingService::process)
                .filter(Objects::nonNull)
                .map(ocrProcessingService::process)
                .map(translateService::findAllWordsTranslations)
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(Schedulers.parallel());
    }
}
