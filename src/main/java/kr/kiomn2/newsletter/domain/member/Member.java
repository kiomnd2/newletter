package kr.kiomn2.newsletter.domain.member;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private LocalDateTime registerAt;

    private LocalDateTime deactivatedAt;

    private LocalDateTime emailVerifiedAt;

    public static Member register(@Valid MemberCommand.MemberRegister requestMember) {
        Member member = new Member();
        member.email = requestMember.email();
        member.password = requestMember.password();
        member.nickname = requestMember.nickname();
        member.status = MemberStatus.PENDING;
        member.registerAt = LocalDateTime.now();
        return member;
    }
}
