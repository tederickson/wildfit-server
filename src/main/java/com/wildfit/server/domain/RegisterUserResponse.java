package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record RegisterUserResponse(String message) {
}
