package com.wildfit.server.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class ShoppingListDigest {
    private Long id;
    private String uuid;

    private List<ShoppingListItemDigest> items;
}
