package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record CreateShoppingListRequest(String uuid, Long mealId) {
}
