package kr.kiomn2.newsletter.application.member;

import kr.kiomn2.newsletter.adapter.persistence.MemberEmailVerificationCodeStore;
import kr.kiomn2.newsletter.application.member.provided.EmailVerifier;
import kr.kiomn2.newsletter.application.member.required.EmailSender;
import kr.kiomn2.newsletter.application.member.utils.VerificationCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberEmailVerificationService implements EmailVerifier {
    private final EmailSender emailSender;
    private final MemberEmailVerificationCodeStore verificationCodeStore;


    @Override
    public void sendVerificationCode(String email) {
        log.info("send verification code to {}", email);

        String verificationCode = VerificationCodeGenerator.createVerificationCode();

        // redis 저장
        verificationCodeStore.storeCode(email, verificationCode);

        // 이메일 전송
        emailSender.sendEmail(email, verificationCode);
    }


}
