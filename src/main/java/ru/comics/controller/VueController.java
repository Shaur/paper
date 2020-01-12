package ru.comics.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.comics.utils.HashUtils;

import java.io.File;
import java.io.IOException;

@Controller
public class VueController {

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/vuepage")
    public String getVuePage(Model model){
        return "blockvue";
    }

    @PostMapping("/vuepage")
    public String uploadFileVue(Model model, @RequestParam("file") MultipartFile file) throws IOException {
        File uploadDir = new File(String.format("%s%s%s", System.getProperty("user.dir"), File.separatorChar, uploadPath));

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String newPath = uploadDir.getPath();
        String originalFileName = file.getOriginalFilename();
        int indexRash = originalFileName.lastIndexOf(".");

        String serverNameFile = String.format("%s.%s", HashUtils.multiPartFiletomd5(file), originalFileName.substring(indexRash + 1));
        String hashMD5 = HashUtils.multiPartFiletomd5(file);
        file.transferTo(new File(newPath + "/" + serverNameFile));
        model.addAttribute("serverName",serverNameFile);
        model.addAttribute("hash",hashMD5);
        model.addAttribute("fileInfo",HashUtils.checkFile(new File(file.getName())));

        return "blockvue";
    }
}
