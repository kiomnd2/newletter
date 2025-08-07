package kr.kiomn2.newsletter.application.member.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class VerificationCodeGenerator {

    public static String createVerificationCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }
}
