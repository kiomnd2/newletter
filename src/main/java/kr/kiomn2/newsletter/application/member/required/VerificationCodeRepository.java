package kr.kiomn2.newsletter.application.member.required;

import java.util.Optional;

public interface VerificationCodeRepository {
    void storeCode(String email, String code);
    Optional<String> readCode(String email);
}
