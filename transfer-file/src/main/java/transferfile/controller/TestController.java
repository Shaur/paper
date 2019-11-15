package transferfile.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController

public class TestController {

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/start")
    public String start() {
        return "start";
    }

    /**
     * Просмотреть директорию загрузки
     * @return upload Path
     */
    @GetMapping("/userdir")
    public String userDir(){
        return String.format("%s%s%s", System.getProperty("user.dir"), File.separatorChar, uploadPath);
    }


}