package com.wildfit.server.domain;

import lombok.Data;

/*
 * https://gist.github.com/mattsilv/2e7a599a48b395f36fa350236a17b4a5
 */
@Data
public final class ParseRecipeRequest {
    private String query;
    private boolean line_delimited = true;
    private String aggregate;   // Recipe name
    private Integer num_servings;

    public void addIngredient(Float servingQty, String servingUnit, String foodName) {
        String ingredient;

        if (servingUnit == null || servingUnit.equals(foodName)) {
            ingredient = "%s %s".formatted(servingQty, foodName);
        } else {
            ingredient = "%s %s %s".formatted(servingQty, servingUnit, foodName);
        }

        if (query == null) {
            query = ingredient;
        } else {
            query += "\n" + ingredient;
        }
    }
}
