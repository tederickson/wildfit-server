package com.wildfit.server.model;

import com.wildfit.server.domain.IngredientType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@PrimaryKeyJoinColumn(name = CommonRecipe.JOIN_KEY)
public class Ingredient extends CommonRecipe {
    @Id
    private Long id; // shows up in database as CommonRecipe.JOIN_KEY
    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(nullable = false)
    private String description;

    @Column(name = "ingredient_serving_qty", nullable = false)
    private Float ingredientServingQty;

    @Column(name = "ingredient_serving_unit", length = 20, nullable = false)
    private String ingredientServingUnit;

    @Column(name = "ingredient_type", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private IngredientType ingredientType;

    public Ingredient() {
        super();
        setType(CommonRecipeType.INGREDIENT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        com.wildfit.server.model.Ingredient that = (com.wildfit.server.model.Ingredient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
