package ru.comics.ocr.service;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.comics.ocr.configuration.MessageQueueChannels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class MQService {

    private final RedisClient redisClient;

    private Map<String, List<Consumer>> consumers = new HashMap<>();

    @Autowired
    public MQService(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public void subscribe(MessageQueueChannels mqs) {
        StatefulRedisPubSubConnection<String, Serializable> connection = redisClient.connectPubSub(mqs.getRedisCodec());
        connection.addListener(new RedisPubSubAdapter<String, Serializable>() {
            @Override
            public void message(String channel, Serializable message) {
                invokeListeners(channel, message);
            }
        });

        connection.async().subscribe(mqs.getChannelName());
    }

    private void invokeListeners(String channel, Serializable message) {
        List<Consumer> listeners = this.consumers.get(channel);
        if (listeners == null)
            return;

        listeners.forEach(consumer -> consumer.accept(message));
    }

    public void publish(MessageQueueChannels mqc, Object value) {
        StatefulRedisPubSubConnection<Object, Object> connection = this.redisClient.connectPubSub(mqc.getRedisCodec());
        connection.reactive().publish(mqc.getChannelName(), value);
    }

    public <T extends Serializable> void addChannelListener (String channelName, Consumer<T> consumer) {
        List<Consumer> consumerList = consumers.get(channelName);
        if (consumerList == null)
            consumerList = new ArrayList<>();

        consumerList.add(consumer);
        consumers.put(channelName, consumerList);
    }

    public void removeListener(String channelName, Consumer<Serializable> consumer) {
        List<Consumer> listeners = this.consumers.get(channelName);
        if(listeners == null)
            return;

        listeners.remove(consumer);
    }
}
