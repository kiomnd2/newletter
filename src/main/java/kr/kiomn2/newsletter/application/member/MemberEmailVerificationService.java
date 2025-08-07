package kr.kiomn2.newsletter.application.member;

import kr.kiomn2.newsletter.application.member.provided.EmailVerifier;
import kr.kiomn2.newsletter.application.member.required.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberEmailVerificationService implements EmailVerifier {
    private final EmailSender emailSender;

    @Override
    public void sendVerificationCode(String email) {
        log.info("send verification code to {}", email);

        // redis 저장

    }
}
