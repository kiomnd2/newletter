package kr.kiomn2.newsletter.application.member.provided;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.kiomn2.newsletter.domain.member.Member;
import kr.kiomn2.newsletter.domain.member.MemberCommand;

public interface MemberRegister {
    Member registeMember(@Valid MemberCommand.MemberRegister memberRegisterCommand);

    Member verifyEmail(@Valid @NotNull Long id, @Valid @NotNull String verificationCode);

}
