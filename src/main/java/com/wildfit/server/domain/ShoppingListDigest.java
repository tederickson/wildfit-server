package com.wildfit.server.domain;

import lombok.Builder;

import java.util.List;

@Builder(setterPrefix = "with", toBuilder = true)
public record ShoppingListDigest(
        Long id,
        String uuid,
        List<ShoppingListItemDigest> items) {
}
