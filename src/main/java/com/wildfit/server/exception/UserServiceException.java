package com.wildfit.server.exception;

@lombok.Getter
public class UserServiceException extends Exception {
    final UserServiceError error;

    public UserServiceException(UserServiceError error) {
        super(error.getMessage());
        this.error = error;
    }

    public UserServiceException(UserServiceError error, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
    }
}
