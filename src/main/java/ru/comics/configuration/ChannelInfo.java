package ru.comics.configuration;

import io.lettuce.core.codec.RedisCodec;

public interface ChannelInfo<V> {
    String getChannelName();

    Class getValueClass();

    RedisCodec<String, V> getRedisCodec();
}
