package kr.kiomn2.newsletter.domain.member.journalist;

import jakarta.persistence.*;
import kr.kiomn2.newsletter.domain.member.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reporter_specialties", joinColumns = @JoinColumn(name = "reporter_id"))
    @Column(name = "category_code")
    private Set<String> specialties = new HashSet<>();

    private JournalistStatus journalistStatus;

    private String organization; // 소속 기관

    private LocalDateTime approvalAt;

    private Long approvedBy;

    @Lob
    private String approvalNote;  // 승인/거부 사유

    private Integer totalArticlesWritten = 0;

    private Integer totalViews = 0;




}
