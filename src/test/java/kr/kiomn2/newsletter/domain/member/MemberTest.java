package kr.kiomn2.newsletter.domain.member;

import kr.kiomn2.newsletter.MemberCommandFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


record MemberTest() {

    @Test
    void memberRegisterCreateTest() {
        MemberCommand.MemberRegister member = MemberCommandFixture.memberRegisterCommand("kiomnd2@test.com");
        Member registeredMember = Member.register(member);

        assertThat(registeredMember.getEmail()).isEqualTo(member.email());
        assertThat(registeredMember.getNickname()).isEqualTo(member.nickname());
        assertThat(registeredMember.getRole()).isNull();
        assertThat(registeredMember.getStatus()).isEqualTo(MemberStatus.PENDING);
    }
}