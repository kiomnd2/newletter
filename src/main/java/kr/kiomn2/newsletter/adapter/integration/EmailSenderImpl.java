package kr.kiomn2.newsletter.adapter.integration;

import kr.kiomn2.newsletter.application.member.required.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmailSenderImpl implements EmailSender {

    @Override
    public void sendEmail(String email, String message) {

    }
}
