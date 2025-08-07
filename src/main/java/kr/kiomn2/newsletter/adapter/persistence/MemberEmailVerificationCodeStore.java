package kr.kiomn2.newsletter.adapter.persistence;

import kr.kiomn2.newsletter.application.member.required.VerificationCodeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberEmailVerificationCodeStore implements VerificationCodeStore {
    private final RedisTemplate<String, String> emailVerificationRedisTemplate;
    private static final String EMAIL_VERIFICATION_PREFIX = "email_verification:";

    @Override
    public void storeCode(String email, String code) {
        String key = EMAIL_VERIFICATION_PREFIX + email;
        emailVerificationRedisTemplate.opsForValue().set(key, code);
    }
}
