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
            ingredient = String.format("%s %s", servingQty, foodName);
        } else {
            ingredient = String.format("%s %s %s", servingQty, servingUnit, foodName);
        }

        if (query == null) {
            query = ingredient;
        } else {
            query += "\n" + ingredient;
        }
    }
}
