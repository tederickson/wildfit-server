package com.wildfit.server.domain;

import lombok.Builder;


@Builder(setterPrefix = "with")
public record InstructionDigest(
        Long id,
        Integer stepNumber,
        String instruction) {
}
