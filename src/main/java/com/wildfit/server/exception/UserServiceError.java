package com.wildfit.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserServiceError {
    MISSING_USER_NAME("missing user name", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("invalid password", HttpStatus.BAD_REQUEST),
    EXISTING_USER("user exists", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
