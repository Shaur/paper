package ru.comics.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.comics.ocr.service.OcrProcessingService;
import ru.comics.ocr.service.PreprocessingService;
import ru.comics.service.ImageProcessingService;
import ru.comics.translater.repository.EnRuWordRepository;
import ru.comics.translater.transfer.Translate;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/image")
public class ImageRestController {

    private final ImageProcessingService imageProcessingService;
    private final PreprocessingService imagePreprocessingService;
    private final OcrProcessingService ocrProcessingService;
    private final EnRuWordRepository enRuWordRepository;

    @Autowired
    public ImageRestController(ImageProcessingService imageProcessingService,
                               PreprocessingService imagePreprocessingService,
                               OcrProcessingService ocrProcessingService,
                               EnRuWordRepository enRuWordRepository
    ) {
        this.imageProcessingService = imageProcessingService;
        this.imagePreprocessingService = imagePreprocessingService;
        this.ocrProcessingService = ocrProcessingService;
        this.enRuWordRepository = enRuWordRepository;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flux<Translate> uploadFile(@RequestPart Mono<FilePart> file) {
        return file
                .map(imageProcessingService::storeFile)
                .map(imagePreprocessingService::process)
                .filter(Objects::nonNull)
                .map(ocrProcessingService::process)
                .map(enRuWordRepository::findAllByWordIn)
                .flatMapMany(Flux::fromIterable);
    }


}
