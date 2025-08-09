package kr.kiomn2.newsletter.application.member;

import kr.kiomn2.newsletter.adapter.persistence.MemberEmailVerificationCodeRepository;
import kr.kiomn2.newsletter.adapter.persistence.config.EmbeddedRedisTestConfig;
import kr.kiomn2.newsletter.application.member.required.EmailSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Import(EmbeddedRedisTestConfig.class)
@SpringBootTest
class MemberEmailVerificationServiceTest {

    @Autowired
    private MemberEmailVerificationCodeRepository memberEmailVerificationCodeStore;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    void memberEmailVerificationTest() {
        MemberEmailVerificationService service = new MemberEmailVerificationService(new MockEmailSender(), memberEmailVerificationCodeStore);

        service.sendVerificationCode("email@email.com");

        String sendCode = redisTemplate.opsForValue().get("email_verification:email@email.com");

        assertThat(sendCode).isNotNull();
    }

    static class MockEmailSender implements EmailSender {
        @Override
        public void sendEmail(String email, String message) {
            System.out.println("sennding email to : " + email + " : " + message);
        }
    }
}