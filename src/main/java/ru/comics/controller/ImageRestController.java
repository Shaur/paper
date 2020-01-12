package ru.comics.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.comics.service.ImageProcessingService;
import ru.comics.translater.transfer.Translate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/image")
public class ImageRestController {

    private final ImageProcessingService imageProcessingService;

    @Autowired
    public ImageRestController(ImageProcessingService imageProcessingService) {
        this.imageProcessingService = imageProcessingService;

    }

    @PostMapping(value = "/upload")
    public List<Translate> uploadFile(@RequestParam MultipartFile file) {
        if (file.isEmpty())
            return null;

        return imageProcessingService.process(file);
    }



}
