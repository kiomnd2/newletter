package kr.kiomn2.newsletter.domain.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class MemberCommand {

    public record MemberRegister(
            @Email String email, @Min(4) @Max(12) String password, @Min(4) @Max(20) String nickname
    ){ }
}
