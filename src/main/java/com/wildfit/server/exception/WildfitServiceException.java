package com.wildfit.server.exception;

import lombok.Getter;

@Getter
public class WildfitServiceException extends Exception {
    final WildfitServiceError error;

    public WildfitServiceException(WildfitServiceError error) {
        super(error.getMessage());
        this.error = error;
    }

    public WildfitServiceException(WildfitServiceError error, String message) {
        super(message);
        this.error = error;
    }
}
