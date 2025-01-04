package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.RecipeListDigest;

import java.util.List;

public final class RecipeListMapper {
    private RecipeListMapper() {
    }

    public static RecipeListDigest map(List<com.wildfit.server.model.Recipe> recipes) {
        if (recipes == null || recipes.isEmpty()) {
            return new RecipeListDigest(List.of());
        }

        return new RecipeListDigest(recipes.stream().map(RecipeSummaryMapper::map).toList());
    }
}