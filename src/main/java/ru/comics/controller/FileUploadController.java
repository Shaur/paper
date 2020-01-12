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
import java.util.Arrays;
import java.util.List;


@Controller
public class FileUploadController {

    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping("/upload")
    public String uploadPage(Model model) {
        File uploadDir = getUploadDir();
        checkDir(uploadDir);
        model.addAttribute("files", getFileListofDir(uploadDir.getName()));
        return "uploadForm";
    }

    private File getUploadDir() {
        return new File(String.format("%s%s%s", System.getProperty("user.dir"), File.separatorChar, uploadPath));
    }

    @PostMapping("/upload")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) throws IOException {
        File uploadDir = getUploadDir();
        String currentPath = String.format("%s%s%s", System.getProperty("user.dir"), File.separatorChar, uploadPath );

        checkDir(uploadDir);
        String newPath = uploadDir.getPath();
        int indexRash = file.getOriginalFilename().lastIndexOf(".");

        String serverNameFile = String.format("%s.%s", HashUtils.multiPartFiletomd5(file), file.getOriginalFilename().substring(indexRash + 1));
        String hashMD5 = HashUtils.multiPartFiletomd5(file);
        file.transferTo(new File(newPath + "/" + serverNameFile));

        model.addAttribute("serverName",serverNameFile);
        model.addAttribute("hash",hashMD5);
        model.addAttribute("fileInfo",HashUtils.checkFile(new File(file.getName())));

        return "block";
    }

    @GetMapping("/block")
    public String testTwo(Model model){
        return "block";
    }

    @GetMapping("/blockvue")
    public String vueStart(Model model){
        return "blockvue";
    }

    private void checkDir(File uploadDir) {
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
    }


    private List<File> getFileListofDir(String uploadDir) {

        File dir = new File(uploadDir);
        File[] arrFiles = dir.listFiles();
        List<File> files = Arrays.asList(arrFiles);

        return files;

    }

    public File findFileName(String fileName, String path) {
        File dirForSearch = new File(path);
        File[] fileList = dirForSearch.listFiles();
        for (File file : fileList) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }

        return null;
    }

    public File findFile(String fileName, String path) {
        String fullPath = path + "/" + fileName;
        File fileSearch = new File(fullPath);
        if (HashUtils.checkFile(fileSearch)) {
            return fileSearch;
        }

        return new File("No File");
    }
}