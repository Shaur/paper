package ru.comics.mq.codec;

import com.google.gson.Gson;
import io.lettuce.core.codec.RedisCodec;

import java.io.Serializable;
import java.nio.ByteBuffer;

public abstract class AbstractStringKeyCodec<V extends Serializable> implements RedisCodec<String, V> {

    @Override
    public String decodeKey(ByteBuffer bytes) {
        return new String(toByteArray(bytes));
    }

    @Override
    public abstract V decodeValue(ByteBuffer bytes);

    @Override
    public ByteBuffer encodeKey(String key) {
        return ByteBuffer.wrap(key.getBytes());
    }

    @Override
    public ByteBuffer encodeValue(V value) {
        byte[] bytes = new Gson().toJson(value).getBytes();
        return ByteBuffer.wrap(bytes);
    }

    /**
     * По какой-то волшебной причине ByteBuffer.array() выкидывает исключение
     * То есть честно считает, что в буфере нет массива.
     * Этот грязный хак призван решить проблему... и он хотя бы работает
     *
     * @param bytes буффер
     * @return массив байт, содержащийся в буфере
     */
    private byte[] toByteArray(ByteBuffer bytes) {
        byte[] byteArray = new byte[bytes.limit()];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = bytes.get(i);
        }

        return byteArray;
    }

    protected V decodeFromJSON(ByteBuffer bytes, Class clazz) {
        return (V) new Gson().fromJson(new String(toByteArray(bytes)), clazz);
    }
}
