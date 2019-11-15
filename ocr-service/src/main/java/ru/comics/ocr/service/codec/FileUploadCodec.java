package ru.comics.ocr.service.codec;

import ru.comics.ocr.transfer.FileUploadInfo;

import java.nio.ByteBuffer;

public class FileUploadCodec extends AbstractStringKeyCodec<FileUploadInfo> {

    @Override
    public FileUploadInfo decodeValue(ByteBuffer bytes) {
        return decodeFromJSON(bytes, FileUploadInfo.class);
    }

}
