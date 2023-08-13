package com.wildfit.server.domain;

import java.util.List;

import lombok.Data;

@Data
public class RecipeListDigest {
    private List<RecipeSummaryDigest> recipes;
}
