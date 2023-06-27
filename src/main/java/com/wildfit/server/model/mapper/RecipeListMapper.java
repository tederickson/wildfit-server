package com.wildfit.server.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.model.Recipe;

public class RecipeListMapper {
    public RecipeListMapper() {
    }

    public static RecipeListDigest map(List<Recipe> recipes) {
        RecipeListDigest recipeListDigest = new RecipeListDigest();
        if (recipes != null) {
            recipeListDigest.setRecipes(recipes.stream().map(RecipeMapper::map).collect(Collectors.toList()));
        }
        return recipeListDigest;
    }
}