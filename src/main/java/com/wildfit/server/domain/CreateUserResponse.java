package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")

public record CreateUserResponse(
        Long id,
        String email,
        String uuid) {
}
