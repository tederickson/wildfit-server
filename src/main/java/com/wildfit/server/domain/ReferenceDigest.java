package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record ReferenceDigest(String type, String description) {
}
