package ru.comics.ocr.controller;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.comics.ocr.service.codec.FileUploadCodec;
import ru.comics.ocr.transfer.FileUploadInfo;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final RedisClient redisClient;

    @Autowired
    public ApiController(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @GetMapping("/set/channel={channel}")
    public void setMessage(@PathVariable("channel") String channel) {
        StatefulRedisPubSubConnection<String, FileUploadInfo> connection = redisClient.connectPubSub(new FileUploadCodec());

        RedisPubSubAsyncCommands<String, FileUploadInfo> async = connection.async();
        async.publish(channel, new FileUploadInfo("a5230c6b1e21da607ef8b8ce2eb15107.png"));
    }

    @GetMapping("/get/channel={channel}")
    public String getMessage(@PathVariable("channel") String channel) {
        RedisCommands<String, String> sync = redisClient.connect().sync();
        return sync.get(channel);
    }

}
