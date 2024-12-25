package com.wildfit.server.domain;

import lombok.Builder;

import java.util.List;

@Builder(setterPrefix = "with")
public record CreateMealRequest(String uuid, List<Long> recipeIds) {
}
