package kr.kiomn2.newsletter.adapter.persistence;

import kr.kiomn2.newsletter.adapter.persistence.config.EmbeddedRedisTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(EmbeddedRedisTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class MemberEmailVerificationCodeStoreTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    void storeEmailVerificationCodeTest() {
        MemberEmailVerificationCodeStore codeStore = new MemberEmailVerificationCodeStore(redisTemplate);

        codeStore.storeCode("kiomnd@test.com", "123456");


    }
}