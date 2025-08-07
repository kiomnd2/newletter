package kr.kiomn2.newsletter.application.member.required;

public interface EmailSender {
    void sendEmail(String email, String message);
}
