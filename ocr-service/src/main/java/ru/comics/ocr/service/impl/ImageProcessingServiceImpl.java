package ru.comics.ocr.service.impl;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.comics.ocr.service.ImageProcessingService;
import ru.comics.ocr.transfer.PageRecognitionInfo;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageProcessingServiceImpl implements ImageProcessingService {

    private final Tesseract tesseract;

    @Autowired
    public ImageProcessingServiceImpl(Tesseract tesseract) {
        this.tesseract = tesseract;
    }

    @Override
    public String ocr(String fileName) {
        try {
            return tesseract.doOCR(new File(String.format("uploads/%s", fileName)));
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public PageRecognitionInfo ocrResultProcessing(String ocr, String fileHash) {
        ocr = ocr.replaceAll("[^a-zA-Z]", " ");

        String[] words = ocr.split(" ");
        List<String> result = Arrays.stream(words)
                .map(String::trim)
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toList());

        return new PageRecognitionInfo(result, fileHash);
    }

    @Override
    public PageRecognitionInfo process(String fileName, String fileHash) {
        String ocr = ocr(fileName);
        return ocrResultProcessing(ocr, fileHash);
    }
}
