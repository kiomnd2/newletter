package kr.kiomn2.newsletter;

import kr.kiomn2.newsletter.domain.member.MemberCommand;

public class MemberCommandFixture {

    public static MemberCommand.MemberRegister memberRegisterCommand(String email) {
        return new MemberCommand.MemberRegister(email, "test11", "test11");
    }
}
