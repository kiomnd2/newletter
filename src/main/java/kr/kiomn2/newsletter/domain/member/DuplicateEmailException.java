package kr.kiomn2.newsletter.domain.member;

import jakarta.validation.constraints.Email;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
