package kr.kiomn2.newsletter.application.member;

import kr.kiomn2.newsletter.application.member.provided.MemberRegister;
import kr.kiomn2.newsletter.application.member.required.MemberRepository;
import kr.kiomn2.newsletter.application.member.required.SubscriberRepository;
import kr.kiomn2.newsletter.domain.member.DuplicateEmailException;
import kr.kiomn2.newsletter.domain.member.Member;
import kr.kiomn2.newsletter.domain.member.MemberCommand;
import kr.kiomn2.newsletter.domain.member.MemberRole;
import kr.kiomn2.newsletter.domain.member.subscriber.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
@Service
public class MemberCommandService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final SubscriberRepository subscriberRepository;
    private final MemberEmailVerificationService memberEmailVerificationService;

    @Transactional
    @Override
    public Member registeMember(MemberCommand.MemberRegister memberRegisterCommand) {
        checkDuplicateEmail(memberRegisterCommand);

        Member register = Member.register(memberRegisterCommand);

        memberEmailVerificationService.sendVerificationCode(register.getEmail());

        return memberRepository.save(register);
    }

    @Transactional
    @Override
    public Member verifyEmail(Long id, String verificationCode) {
        Member member = memberRepository.findById(id).orElseThrow();

        memberEmailVerificationService.checkVerificationCode(member.getEmail(), verificationCode);

        member.verifyEmail();

        return member;
    }

    // 구독자로 신청
    @Transactional
    public Subscriber applySubscriber(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow();

        // 이메일 인증 체크
        checkEmail(member);

        checkRoles(member, MemberRole.SUBSCRIBER);

        member.addRole(MemberRole.SUBSCRIBER);

        return createSubscriber(member);
    }

    private static void checkRoles(Member member, MemberRole role) {
        if (member.hasRole(role)) {
            throw new IllegalStateException("이미 해당 역할을 보유하고 있습니다.");
        }
    }

    private Subscriber createSubscriber(Member member) {
        Subscriber subscriber = Subscriber.apply(member);
        return subscriberRepository.save(subscriber);
    }

    private static void checkEmail(Member member) {
        if (!member.isEmailVerified()) {
            throw new IllegalStateException("이메일 인증이 완료되지 않은 회원입니다.");
        }
    }

    private void checkDuplicateEmail(MemberCommand.MemberRegister registerRequest) {
        if (memberRepository.findByEmail(registerRequest.email()).isPresent()) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다. " + registerRequest.email());
        }
    }
}
