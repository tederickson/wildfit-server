package com.wildfit.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserServiceError {
    MISSING_EMAIL("Missing email.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("Invalid password.", HttpStatus.BAD_REQUEST),
    INVALID_NAME("Missing name.", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER("Invalid parameter.", HttpStatus.BAD_REQUEST),
    INVALID_PAGE_SIZE("Invalid page size", HttpStatus.BAD_REQUEST),
    INVALID_PAGE_OFFSET("Invalid page number", HttpStatus.BAD_REQUEST),
    INVALID_CONFIRMATION_CODE("Invalid confirmation code.", HttpStatus.BAD_REQUEST),
    EXISTING_USER("User exists.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("User not found.", HttpStatus.NOT_FOUND),
    RECIPE_NOT_FOUND("Recipe not found.", HttpStatus.NOT_FOUND),
    RECIPE_GROUP_NOT_FOUND("Recipe not found.", HttpStatus.NOT_FOUND),
    NOT_REGISTERED("Your account isn't active or hasn't been approved yet.", HttpStatus.BAD_REQUEST),
    NOT_AUTHORIZED("Your account not authorized.", HttpStatus.UNAUTHORIZED),
    EMAIL_NOT_CONFIGURED("The email server is not configured.", HttpStatus.PRECONDITION_FAILED);

    private final String message;
    private final HttpStatus httpStatus;
}
