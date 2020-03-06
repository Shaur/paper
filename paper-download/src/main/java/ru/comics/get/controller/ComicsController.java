package ru.comics.get.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.comics.get.auth.exception.AuthenticationException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reader")
public class ComicsController {

    @Value("${storage.savePath}")
    private String storePath;
    
    @GetMapping("/{series}/{number}/{page}")
    public void getPage(@PathVariable Long series, 
                        @PathVariable Integer number, 
                        @PathVariable Integer page,
                        HttpServletResponse response
    ) throws IOException {
        var subFolder = String.format("%s/%s/%s", storePath, series, number);
        var folderPath = Paths.get(subFolder);
        var file = Objects.requireNonNull(folderPath.toFile().listFiles())[page];
        Files.copy(file.toPath(), response.getOutputStream());
    }
    
    @GetMapping("/read/{series}/{number}")
    @ExceptionHandler(AuthenticationException.class)
    public List<String> getNumberPages(@PathVariable Long series, @PathVariable Long number) {
        var subFolder = String.format("%s/%s/%s", storePath, series, number);
        var folderPath = Paths.get(subFolder);
        var length = Objects.requireNonNull(folderPath.toFile().listFiles()).length;
        
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            urls.add(String.format("/reader/%s/%s/%s", series, number, i));
        }
        
        return urls;
    }
}
