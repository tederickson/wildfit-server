package com.wildfit.server.exception;

@lombok.Getter
public class UserServiceException extends Exception {
    final UserServiceError error;

    public UserServiceException(UserServiceError error) {
        super(error.getMessage());

        this.error = error;
    }
}
