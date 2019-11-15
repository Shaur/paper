package transferfile.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.comics.mq.MQService;

@Configuration
public class RedisMessageConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    @Bean
    public MQService mqService() {
        RedisURI redisURI = RedisURI.Builder.redis(host,port).withDatabase(1).build();
        return new MQService(RedisClient.create(redisURI));
    }



}
