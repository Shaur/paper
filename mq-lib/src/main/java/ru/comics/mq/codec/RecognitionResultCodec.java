package ru.comics.mq.codec;

import ru.comics.mq.transfer.PageRecognitionInfo;

import java.nio.ByteBuffer;

public class RecognitionResultCodec extends AbstractStringKeyCodec<PageRecognitionInfo> {

    @Override
    public PageRecognitionInfo decodeValue(ByteBuffer bytes) {
        return decodeFromJSON(bytes, PageRecognitionInfo.class);
    }
}
