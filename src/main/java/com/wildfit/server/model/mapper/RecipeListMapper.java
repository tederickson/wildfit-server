package com.wildfit.server.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.model.Recipe1;

public class RecipeListMapper {
    private RecipeListMapper() {
    }

    public static RecipeListDigest map(List<Recipe1> recipes) {
        RecipeListDigest recipeListDigest = new RecipeListDigest();
        if (recipes != null) {
            recipeListDigest.setRecipes(recipes.stream().map(RecipeMapper::toSummary).collect(Collectors.toList()));
        }
        return recipeListDigest;
    }

}