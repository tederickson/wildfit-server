package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record CreateUserRequest(String email, String password, String name) {
}
