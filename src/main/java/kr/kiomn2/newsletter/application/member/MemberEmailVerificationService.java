package kr.kiomn2.newsletter.application.member;

import kr.kiomn2.newsletter.adapter.persistence.MemberEmailVerificationCodeRepository;
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
    private final MemberEmailVerificationCodeRepository verificationCodeRepository;


    @Override
    public void sendVerificationCode(String email) {
        log.info("send verification code to {}", email);

        String verificationCode = VerificationCodeGenerator.createVerificationCode();

        // redis 저장
        verificationCodeRepository.storeCode(email, verificationCode);

        // 이메일 전송
        emailSender.sendEmail(email, verificationCode);
    }

    @Override
    public void checkVerificationCode(String email, String verificationCode) {
        String findCode = verificationCodeRepository
                .readCode(email)
                .orElseThrow(() -> new IllegalStateException("인증코드가 만료되었거나 찾을 수 없습니다 " + verificationCode));

        if (!findCode.equals(verificationCode)) {
            throw new IllegalStateException("인증코드가 일치하지 않습니다.");
        }
    }


}
