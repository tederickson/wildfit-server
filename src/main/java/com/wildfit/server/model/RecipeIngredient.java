package com.wildfit.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe_ingredient", indexes = {
        @Index(name = "recipe_idx1", columnList = "recipe_id"),
        @Index(name = "food_name_idx", columnList = "food_name")})
public final class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "recipe_id")
    private long recipeId;                 // Recipe.id
    @Column(name = "instruction_group_id")
    private long instructionGroupId;       // InstructionGroup.id

    @Column(name = "food_name")
    private String foodName;
    private String description;

    @Column(name = "serving_qty")
    private Float servingQty;
    @Column(name = "serving_unit")
    private String servingUnit;
    @Column(name = "ingredient_serving_qty")
    private Float ingredientServingQty;
    @Column(name = "ingredient_serving_unit")
    private String ingredientServingUnit;

    @Column(name = "ingredient_type")
    private String ingredientType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecipeIngredient that = (RecipeIngredient) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
