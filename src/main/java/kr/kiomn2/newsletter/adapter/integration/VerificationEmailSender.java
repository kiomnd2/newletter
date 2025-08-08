package kr.kiomn2.newsletter.adapter.integration;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.kiomn2.newsletter.application.member.required.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class VerificationEmailSender implements EmailSender {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String email, String verificationCode) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom("kiomnd2@test.com");
            helper.setTo(email);
            helper.setSubject("뉴스레터 서비스 이메일 인증");

            helper.setText("인증 이메일 = " + verificationCode);

            javaMailSender.send(mimeMessage);
            log.info("인증 이메일이 성공적으로 발송되었습니다. to: {}", email);
        } catch (MessagingException e) {
            log.error("이메일 발송중 오류가 발생했습니다. to: {}", email, e);
            throw new RuntimeException("이메일 발송에 실패했습니다.", e);
        }
    }
}
