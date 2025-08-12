package kr.kiomn2.newsletter.application.member;

import kr.kiomn2.newsletter.MemberCommandFixture;
import kr.kiomn2.newsletter.application.member.required.MemberRepository;
import kr.kiomn2.newsletter.application.member.required.SubscriberRepository;
import kr.kiomn2.newsletter.domain.member.Member;
import kr.kiomn2.newsletter.domain.member.MemberCommand;
import kr.kiomn2.newsletter.domain.member.MemberRole;
import kr.kiomn2.newsletter.domain.member.MemberStatus;
import kr.kiomn2.newsletter.domain.member.subscriber.Subscriber;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class MemberCommandServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Mock
    private MemberEmailVerificationService memberEmailVerificationService;

    @Test
    void memberRegisterTest() {
        MemberCommand.MemberRegister memberRegisterCommand = MemberCommandFixture.memberRegisterCommand("tess@test.com");

        MemberCommandService memberCommandService = new MemberCommandService(memberRepository, subscriberRepository, memberEmailVerificationService);

        // registerMember Member
        Member member = memberCommandService.registeMember(memberRegisterCommand);

        Member findMember = memberRepository.findById(member.getId()).get();

        assertThat(findMember.getId()).isNotNull();
        assertThat(findMember.getRegisterAt()).isNotNull();
        assertThat(findMember.getRoles().size()).isEqualTo(0);
        assertThat(findMember.getEmail()).isEqualTo(memberRegisterCommand.email());
        assertThat(findMember.getPassword()).isEqualTo(memberRegisterCommand.password());
        assertThat(findMember.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void applySubscriberTest() {
        MemberCommand.MemberRegister memberRegisterCommand = MemberCommandFixture.memberRegisterCommand("tess@test.com");

        MemberCommandService memberCommandService = new MemberCommandService(memberRepository, subscriberRepository, memberEmailVerificationService);

        // registerMember Member
        Member member = memberCommandService.registeMember(memberRegisterCommand);
        member.verifyEmail();

        Subscriber subscriber = memberCommandService.applySubscriber(member.getEmail());

        assertThat(subscriber.getMember().getId()).isEqualTo(member.getId());
        assertThat(subscriber.getMember().getEmail()).isEqualTo(member.getEmail());
        assertThat(subscriber.getCreatedAt()).isNotNull();
        assertThat(subscriber.getMember()).isEqualTo(member);
        assertThat(subscriber.getSubscribedCategories().size()).isEqualTo(0);
        assertThat(member.hasRole(MemberRole.SUBSCRIBER)).isTrue();

    }

}