package com.wildfit.server.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class WildfitServiceErrorTest {

    @Test
    void getHttpStatus() {
        assertEquals(HttpStatus.NOT_FOUND, WildfitServiceError.USER_NOT_FOUND.getHttpStatus());
    }
}