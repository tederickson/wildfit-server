package com.wildfit.server.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WildfitServiceErrorTest {

    @Test
    void getHttpStatus() {
        assertEquals(HttpStatus.NOT_FOUND, WildfitServiceError.USER_NOT_FOUND.getHttpStatus());
    }
}