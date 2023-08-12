package com.wildfit.server.model;

import java.util.Objects;

import com.wildfit.server.domain.IngredientType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@PrimaryKeyJoinColumn(name = CommonRecipe.JOIN_KEY)
public class Ingredient extends CommonRecipe {
    @Id
    private Long id; // shows up in database as CommonRecipe.JOIN_KEY
    @Column(name = "food_name")
    private String foodName;
    private String description;

    @Column(name = "ingredient_serving_qty")
    private Float ingredientServingQty;

    @Column(name = "ingredient_serving_unit", length = 20)
    private String ingredientServingUnit;

    @Column(name = "ingredient_type", length = 20)
    private String ingredientType;

    public Ingredient() {
        super();
        setType(CommonRecipeType.INGREDIENT);
    }

    public Ingredient setIngredientType(IngredientType ingredientType) {
        if (ingredientType != null) {
            this.ingredientType = ingredientType.toString();
        }
        return this;
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
        return id == that.id
                && Objects.equals(foodName, that.foodName)
                && Objects.equals(description, that.description)
                && Objects.equals(ingredientServingQty, that.ingredientServingQty)
                && Objects.equals(ingredientServingUnit, that.ingredientServingUnit)
                && Objects.equals(ingredientType, that.ingredientType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foodName, description, ingredientServingQty, ingredientServingUnit, ingredientType);
    }
}
