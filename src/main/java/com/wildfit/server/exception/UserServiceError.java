package com.wildfit.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserServiceError {
    MISSING_EMAIL("missing email", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("invalid password", HttpStatus.BAD_REQUEST),
    INVALID_CONFIRMATION_CODE("invalid confirmation code", HttpStatus.BAD_REQUEST),
    EXISTING_USER("user exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("user not found", HttpStatus.NOT_FOUND),
    NOT_REGISTERED("user not registered", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
