package kr.kiomn2.newsletter.domain.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MemberCommand {

    public record MemberRegister(
            @NotNull @Email String email, @NotNull @Min(4) @Max(12) String password, @NotNull @Min(4) @Max(20) String nickname
    ){ }
}
