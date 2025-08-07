package kr.kiomn2.newsletter.adapter.persistence.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.net.ServerSocket;

@TestConfiguration
@Profile("test")
public class EmbeddedRedisTestConfig {

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
        int port = findAvailablePort();
        redisServer = RedisServer.builder()
                .port(port)
                .build();

        redisServer.start();
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
}
