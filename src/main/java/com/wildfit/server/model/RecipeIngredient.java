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
    private String foodName; //  Butter, Pure Irish, Unsalted,
    @Column(name = "brand_name")
    private String brandName; //  Kerrygold,
    @Column(name = "brand_name_item_name")
    private String brandNameItemName;
    @Column(name = "serving_qty")
    private Integer servingQty; //  1,
    @Column(name = "serving_unit")
    private String servingUnit; //  tbsp,
    @Column(name = "ingredient_serving_qty")
    private Integer ingredientServingQty; //  1,
    @Column(name = "ingredient_serving_unit")
    private String ingredientServingUnit; //  tbsp,
    @Column(name = "serving_weight_grams")
    private Integer servingWeightGrams; //  14,
    @Column(name = "metric_qty")
    private Integer metricQty; //  14,
    @Column(name = "metric_uom")
    private String metricUom; //  g,
    private Float calories; //  100,
    @Column(name = "total_fat")
    private Float totalFat; //  12,
    @Column(name = "saturated_fat")
    private Float saturatedFat; //  8,
    private Float cholesterol; //  30,
    private Float sodium; //  0,
    @Column(name = "total_carbohydrate")
    private Float totalCarbohydrate; //  0,
    @Column(name = "dietary_fiber")
    private Float dietaryFiber; //  null,
    private Float sugars; //  null,
    private Float protein; //  0,
    private Float potassium; //  null,
    private Float phosphorus; //  null,
    private Float calcium;
    private Float iron;
    @Column(name = "vitamin_d")
    private Float vitaminD;
    @Column(name = "added_sugars")
    private Float addedSugars;
    @Column(name = "trans_fatty_acid")
    private Float transFattyAcid;
    @Column(name = "nix_brand_name")
    private String nixBrandName; //  Kerrygold,
    @Column(name = "nix_brand_id")
    private String nixBrandId; //  51db37b7176fe9790a8989b4,
    @Column(name = "nix_item_id")
    private String nixItemId; //  52a15041d122497b50000a75,

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
