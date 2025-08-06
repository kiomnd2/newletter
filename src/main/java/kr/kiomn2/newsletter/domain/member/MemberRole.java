package kr.kiomn2.newsletter.domain.member;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberRole {
    SUBSCRIBER, JOURNALIST;
}
