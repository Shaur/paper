package ru.comics.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import ru.comics.service.ImageService;

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
    public ResponseEntity<HttpStatus> uploadFile(@RequestPart FilePart file) {
        var storeFile = imageService.storeFile(file);
        imageService.processFile(storeFile);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
