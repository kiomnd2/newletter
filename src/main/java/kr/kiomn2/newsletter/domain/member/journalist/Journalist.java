package kr.kiomn2.newsletter.domain.member.journalist;

import jakarta.persistence.*;
import kr.kiomn2.newsletter.domain.member.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "journalists")
public class Journalist {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;

    private String displayName; // 기자명

    @Lob
    private String bio; // 기자 소개

    private JournalistStatus journalistStatus;

    private String organization; // 소속 기관

    private LocalDateTime approvalAt;

    private Long approvedBy;


}
