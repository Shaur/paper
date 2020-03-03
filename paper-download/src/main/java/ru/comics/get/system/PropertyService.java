package ru.comics.get.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PropertyService {

    private final String savePath;

    public PropertyService(@Value("${storage.savePath}") String savePath) {
        this.savePath = savePath;
    }
    
    public Path createSavePath(Long seriesId, Long numberId) {
        var rawTitle = String.format("%s/%s/%s", savePath, seriesId, numberId);
        var fileName = numberId + ".cbr";

        var pathToFolder = Paths.get(rawTitle).toFile();
        pathToFolder.mkdirs();
        
        return pathToFolder.toPath().resolve(fileName);
    }
    
    @PostConstruct
    private void postConstruct() {
        (new File(savePath)).mkdirs();
    }
}
