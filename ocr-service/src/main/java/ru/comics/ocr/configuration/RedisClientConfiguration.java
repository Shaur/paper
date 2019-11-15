package ru.comics.ocr.configuration;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisClientConfiguration {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    @Bean(name = "redisClient")
    public RedisClient redisClient() {
        RedisURI redisURI = RedisURI.Builder
                .redis(host, port)
                .withDatabase(1)
                .build();

        return RedisClient.create(redisURI);
    }
}
