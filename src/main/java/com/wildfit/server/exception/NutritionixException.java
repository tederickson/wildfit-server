package com.wildfit.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NutritionixException extends Exception {
    final HttpStatus status;

    public NutritionixException(HttpStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }
}
