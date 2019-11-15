package ru.comics.mq.configuration;

import io.lettuce.core.codec.RedisCodec;
import ru.comics.mq.codec.FileUploadCodec;
import ru.comics.mq.codec.RecognitionResultCodec;
import ru.comics.mq.transfer.FileUploadInfo;
import ru.comics.mq.transfer.PageRecognitionInfo;

public enum MessageQueueChannels implements ChannelInfo {
    FILE {
        @Override
        public String getChannelName() {
            return "file";
        }

        @Override
        public Class getValueClass() {
            return FileUploadInfo.class;
        }

        @Override
        public RedisCodec<String, FileUploadInfo> getRedisCodec() {
            return new FileUploadCodec();
        }
    },

    RECOGNITION_RESULT {
        @Override
        public String getChannelName() {
            return "recognitionResult";
        }

        @Override
        public Class getValueClass() {
            return PageRecognitionInfo.class;
        }

        @Override
        public RedisCodec<String, PageRecognitionInfo> getRedisCodec() {
            return new RecognitionResultCodec();
        }
    }
}
