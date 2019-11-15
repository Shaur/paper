package ru.comics.mq.codec;



import ru.comics.mq.transfer.FileUploadInfo;

import java.nio.ByteBuffer;

public class FileUploadCodec extends AbstractStringKeyCodec<FileUploadInfo> {

    @Override
    public FileUploadInfo decodeValue(ByteBuffer bytes) {
        return decodeFromJSON(bytes, FileUploadInfo.class);
    }

}
