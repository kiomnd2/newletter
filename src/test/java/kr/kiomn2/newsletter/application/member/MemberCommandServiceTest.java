package kr.kiomn2.newsletter.application.member;

import kr.kiomn2.newsletter.MemberCommandFixture;
import kr.kiomn2.newsletter.application.member.required.MemberRepository;
import kr.kiomn2.newsletter.domain.member.Member;
import kr.kiomn2.newsletter.domain.member.MemberCommand;
import kr.kiomn2.newsletter.domain.member.MemberStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberCommandServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Mock
    private MemberEmailVerificationService memberEmailVerificationService;

    @Test
    void memberRegisterTest() {
        MemberCommand.MemberRegister memberRegisterCommand = MemberCommandFixture.memberRegisterCommand("tess@test.com");

        MemberCommandService memberCommandService = new MemberCommandService(memberRepository, memberEmailVerificationService);

        // registerMember Member
        Member member = memberCommandService.registeMember(memberRegisterCommand);

        Member findMember = memberRepository.findById(member.getId()).get();

        assertThat(findMember.getId()).isNotNull();
        assertThat(findMember.getRegisterAt()).isNotNull();
        assertThat(findMember.getRole()).isNull();
        assertThat(findMember.getEmail()).isEqualTo(memberRegisterCommand.email());
        assertThat(findMember.getPassword()).isEqualTo(memberRegisterCommand.password());
        assertThat(findMember.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

}