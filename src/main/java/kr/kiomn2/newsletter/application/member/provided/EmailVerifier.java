package kr.kiomn2.newsletter.application.member.provided;

public interface EmailVerifier {
    void sendVerificationCode(String email);
    void checkVerificationCode(String email, String verificationCode);
}
