package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record PhotoDigest(String thumb) {
}
