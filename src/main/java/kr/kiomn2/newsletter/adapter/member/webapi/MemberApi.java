package kr.kiomn2.newsletter.adapter.member.webapi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.kiomn2.newsletter.adapter.member.common.response.CommonResponse;
import kr.kiomn2.newsletter.adapter.member.common.response.dto.MemberResponse;
import kr.kiomn2.newsletter.application.member.MemberCommandService;
import kr.kiomn2.newsletter.domain.member.Member;
import kr.kiomn2.newsletter.domain.member.MemberCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApi {
    private final MemberCommandService memberCommandService;

    @PostMapping("/register")
    public CommonResponse<MemberResponse.MemberRegister> registerMember(@Valid @RequestBody MemberCommand.MemberRegister memberRegister) {
        Member member = memberCommandService.registeMember(memberRegister);
        return CommonResponse.success(MemberResponse.MemberRegister.builder()
                .id(member.getId()).email(member.getEmail()).build());
    }

    @PostMapping("/verify-email/{id}/{verificationCode}")
    public CommonResponse<MemberResponse.MemberRegister> verificationEmail(@Valid @PathVariable @NotNull Long id,
                                                                           @Valid @PathVariable @NotNull String verificationCode) {
        Member member = memberCommandService.verifyEmail(id, verificationCode);
        return CommonResponse.success(MemberResponse.MemberRegister.builder()
                        .email(member.getEmail())
                        .id(member.getId())
                .build());
    }
}

