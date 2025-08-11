package kr.kiomn2.newsletter.application.member;

import kr.kiomn2.newsletter.application.member.provided.MemberRegister;
import kr.kiomn2.newsletter.application.member.required.MemberRepository;
import kr.kiomn2.newsletter.domain.member.DuplicateEmailException;
import kr.kiomn2.newsletter.domain.member.Member;
import kr.kiomn2.newsletter.domain.member.MemberCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
@Service
public class MemberCommandService implements MemberRegister {
    private final MemberRepository memberRepository;
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

    private void checkDuplicateEmail(MemberCommand.MemberRegister registerRequest) {
        if (memberRepository.findByEmail(registerRequest.email()).isPresent()) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다. " + registerRequest.email());
        }
    }
}
