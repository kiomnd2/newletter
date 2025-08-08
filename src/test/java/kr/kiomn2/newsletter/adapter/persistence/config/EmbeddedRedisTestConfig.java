package kr.kiomn2.newsletter.adapter.persistence.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.net.ServerSocket;

@TestConfiguration
@Profile("test")
public class EmbeddedRedisTestConfig {

    private RedisServer redisServer;
    int redisPort = 6379;

    @PostConstruct
    public void startRedis() throws IOException {
        int port = findAvailablePort();
        redisServer = RedisServer.builder()
                .port(port)
                .build();

        System.setProperty("spring.data.redis.port", String.valueOf(port));
        System.setProperty("spring.data.redis.host", "localhost");
        redisServer.start();

        System.out.println("startRedisServer localhost : " + port);
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null && redisServer.isActive()) {
            redisServer.stop();
        }
    }

    private int findAvailablePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    // Redis Connection Factory를 직접 생성하여 포트 문제 해결
    @Bean
    @Primary
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", redisPort);
    }

    // RedisTemplate Bean도 직접 생성
    @Bean
    @Primary
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
