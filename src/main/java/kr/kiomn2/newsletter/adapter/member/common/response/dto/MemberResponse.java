package kr.kiomn2.newsletter.adapter.member.common.response.dto;

import lombok.Builder;

public class MemberResponse {

    @Builder
    public record MemberRegister(Long id, String email) {
    }

    @Builder
    public record ApplySubscriber(Long id, String email) {
    }
}
