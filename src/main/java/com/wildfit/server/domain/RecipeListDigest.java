package com.wildfit.server.domain;

import lombok.Data;

import java.util.List;

@Data
public class RecipeListDigest {
    private List<RecipeSummaryDigest> recipes;
}
