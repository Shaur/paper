package transferfile.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
public class ImageComtroller {


    @Value("${upload.path}")
    private String uploadPath;

    /**
     * Возвращает файл про get запросу
     * @param fileName
     * @return ByteArrayResource
     * @throws IOException
     */

    @RequestMapping(path = "/uploads/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName) throws IOException {

        File file = getUploadPath(fileName);

        HttpHeaders header = new HttpHeaders();

        //Name download
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    private File getUploadPath(@PathVariable("fileName") String fileName) {
        return new File(String.format("%s%s%s", System.getProperty("user.dir"), File.separatorChar, uploadPath +"/" + fileName));
    }
}
