package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")

public record UserDigest(Long id, String email, String uuid, UserStatusType status) {
}
