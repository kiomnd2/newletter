package kr.kiomn2.newsletter.domain.member.journalist;

public enum JournalistStatus {
    PENDING_APPROVAL,  // 승인 대기
    ACTIVE,           // 활성
    SUSPENDED,        // 정지
    REJECTED,         // 거부
    INACTIVE          // 비활성
}
