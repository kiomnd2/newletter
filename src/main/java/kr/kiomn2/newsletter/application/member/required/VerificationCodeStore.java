package kr.kiomn2.newsletter.application.member.required;

public interface VerificationCodeStore {
    void storeCode(String email, String code);
}
