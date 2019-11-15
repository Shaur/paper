package ru.comics.ocr.service.codec;

import ru.comics.ocr.transfer.PageRecognitionInfo;

import java.nio.ByteBuffer;

public class RecognitionResultCodec extends AbstractStringKeyCodec<PageRecognitionInfo> {

    @Override
    public PageRecognitionInfo decodeValue(ByteBuffer bytes) {
        return decodeFromJSON(bytes, PageRecognitionInfo.class);
    }
}
