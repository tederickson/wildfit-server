package com.wildfit.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

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
@Table(name = "recipe_ingredient", indexes = {@Index(name = "recipe_idx1", columnList = "recipe_id")})
public class RecipeIngredient {
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
    @Column(name = "brand_name")
    private String brandName;
    @Column(name = "brand_name_item_name")
    private String brandNameItemName;
    @Column(name = "serving_qty")
    private Float servingQty;
    @Column(name = "serving_unit")
    private String servingUnit;
    @Column(name = "ingredient_serving_qty")
    private Float ingredientServingQty;
    @Column(name = "ingredient_serving_unit")
    private String ingredientServingUnit;
    @Column(name = "serving_weight_grams")
    private Float servingWeightGrams;
    @Column(name = "metric_qty")
    private Float metricQty;
    @Column(name = "metric_uom")
    private String metricUom;
    private Float calories;
    @Column(name = "total_fat")
    private Float totalFat;
    @Column(name = "saturated_fat")
    private Float saturatedFat;
    private Float cholesterol;
    private Float sodium;
    @Column(name = "total_carbohydrate")
    private Float totalCarbohydrate;
    @Column(name = "dietary_fiber")
    private Float dietaryFiber;
    private Float sugars;
    private Float protein;
    private Float potassium;
    private Float phosphorus;
    private Float calcium;
    private Float iron;
    @Column(name = "vitamin_d")
    private Float vitaminD;
    @Column(name = "added_sugars")
    private Float addedSugars;
    @Column(name = "trans_fatty_acid")
    private Float transFattyAcid;
    @Column(name = "nix_brand_name")
    private String nixBrandName;
    @Column(name = "nix_brand_id")
    private String nixBrandId;
    @Column(name = "nix_item_id")
    private String nixItemId;

    private String photo_thumbnail;

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
