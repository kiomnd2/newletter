package kr.kiomn2.newsletter.domain.member;

import jakarta.validation.constraints.*;

public class MemberCommand {

    public record MemberRegister(
            @NotNull @Email String email, @NotNull @Size(min = 4, max = 20) String password, @NotNull @Size(min = 5, max = 20) String nickname
    ){ }
}
