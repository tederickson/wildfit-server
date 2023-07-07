package com.wildfit.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class IngredientDigest {
    private long id;
    private long recipeId;
    private long instructionGroupId;

    private String foodName;
    private String description;
    private String brandName;
    private String brandNameItemName;
    private Float servingQty;
    private String servingUnit;
    private Float ingredientServingQty;
    private String ingredientServingUnit;
    private Float servingWeightGrams;
    private Float metricQty;
    private String metricUom;
    private Float calories;
    private Float totalFat;
    private Float saturatedFat;
    private Float cholesterol;
    private Float sodium;
    private Float totalCarbohydrate;
    private Float dietaryFiber;
    private Float sugars;
    private Float protein;
    private Float potassium;
    private Float phosphorus;
    private Float calcium;
    private Float iron;
    private Float vitaminD;
    private Float addedSugars;
    private Float transFattyAcid;
    private String nixBrandName;
    private String nixBrandId;
    private String nixItemId;
    private PhotoDigest photo;
    private IngredientType ingredientType;
}
