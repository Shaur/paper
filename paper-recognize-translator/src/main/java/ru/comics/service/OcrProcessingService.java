package ru.comics.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OcrProcessingService {

    private final Tesseract tesseract;

    @Autowired
    public OcrProcessingService(Tesseract tesseract) {
        this.tesseract = tesseract;
    }
    
    private String ocr(Path filePath) {
        try {
            return tesseract.doOCR(filePath.toFile());
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        return "";
    }


    private String ocr(BufferedImage img) {
        try {
            return tesseract.doOCR(img);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        
        return "";
    }
    
    private Collection<String> ocrResultProcessing(String ocr) {
        ocr = ocr.replaceAll("[^a-zA-Z]", " ");

        var words = ocr.split(" ");
        return Arrays.stream(words)
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(word -> !word.isEmpty())
                .filter(word -> word.length() > 1)
                .collect(Collectors.toSet());
    }
    
    public Collection<String> process(Path filePath) {
        var ocr = ocr(filePath);
        return ocrResultProcessing(ocr);
    }
    
    public Collection<String> process(BufferedImage img) {
        var ocr = ocr(img);
        return ocrResultProcessing(ocr);
    }

}
