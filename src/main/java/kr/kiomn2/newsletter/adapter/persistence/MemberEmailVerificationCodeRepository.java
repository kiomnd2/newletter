package kr.kiomn2.newsletter.adapter.persistence;

import kr.kiomn2.newsletter.application.member.required.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MemberEmailVerificationCodeRepository implements VerificationCodeRepository {
    private final RedisTemplate<String, String> emailVerificationRedisTemplate;
    private static final String EMAIL_VERIFICATION_PREFIX = "email_verification:";

    @Override
    public void storeCode(String email, String code) {
        String key = EMAIL_VERIFICATION_PREFIX + email;
        emailVerificationRedisTemplate.opsForValue().set(key, code);
    }

    @Override
    public Optional<String> readCode(String email) {
        return Optional.ofNullable(emailVerificationRedisTemplate.opsForValue().get(EMAIL_VERIFICATION_PREFIX + email));
    }


}
