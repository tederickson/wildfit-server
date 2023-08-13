package com.wildfit.server.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.wildfit.server.domain.RecipeListDigest;

public class RecipeListMapper {
    private RecipeListMapper() {
    }

    public static RecipeListDigest map(List<com.wildfit.server.model.Recipe> recipes) {
        final var recipeListDigest = new RecipeListDigest();

        if (recipes == null) {
            recipeListDigest.setRecipes(List.of());
        } else {
            recipeListDigest.setRecipes(recipes.stream().map(RecipeSummaryMapper::map).collect(Collectors.toList()));
        }
        return recipeListDigest;
    }

}