package ru.comics.ocr.service;

import ru.comics.ocr.transfer.PageRecognitionInfo;

public interface ImageProcessingService {
    String ocr(String fileName);
    PageRecognitionInfo ocrResultProcessing(String ocr, String fileHash);
    PageRecognitionInfo process(String fileName, String fileHash);
}
