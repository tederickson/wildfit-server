package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record LoginRequest(String email, String password) {
}
