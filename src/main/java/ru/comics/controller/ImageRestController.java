package ru.comics.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.comics.service.ImageService;
import ru.comics.translater.transfer.Translate;

@Slf4j
@RestController
@RequestMapping("/image")
public class ImageRestController {

    private final ImageService imageService;

    @Autowired
    public ImageRestController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flux<Translate> uploadFile(@RequestPart Mono<FilePart> file) {
        return imageService.processImage(file);

    }


}
