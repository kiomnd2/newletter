package kr.kiomn2.newsletter.adapter.persistence;

import kr.kiomn2.newsletter.adapter.persistence.config.EmbeddedRedisTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Import(EmbeddedRedisTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class MemberEmailVerificationCodeRepositoryTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    void storeEmailVerificationCodeTest() {
        MemberEmailVerificationCodeRepository codeStore = new MemberEmailVerificationCodeRepository(redisTemplate);

        String mockCode = "123456";

        codeStore.storeCode("kiomnd@test.com", mockCode);

        String result = redisTemplate.opsForValue().get("email_verification:kiomnd@test.com");

        assertThat(result).isEqualTo(mockCode);
    }
}