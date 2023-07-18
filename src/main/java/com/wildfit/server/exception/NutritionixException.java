package com.wildfit.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class NutritionixException extends Exception {
    final HttpStatusCode statusCode;

    public NutritionixException(HttpStatusCode statusCode, Throwable cause) {
        super(cause);
        this.statusCode = statusCode;
    }
}
