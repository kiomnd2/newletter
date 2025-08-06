package kr.kiomn2.newsletter.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberStatus {
    PENDING("가입전"), ACTIVE("활성화") ,DEACTIVATED("비활성화");

    private final String description;

    public MemberStatus get(final String value) {
        MemberStatus[] values = values();
        for (MemberStatus memberStatus : values) {
            if (value.equals(memberStatus.name())) {
                return memberStatus;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + value + "]");
    }
}
