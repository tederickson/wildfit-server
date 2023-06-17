package com.wildfit.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemDigest {
    private String foodName; //  Butter, Pure Irish, Unsalted,
    private String brandName; //  Kerrygold,
    private String brandNameItemName;
    private Integer servingQty; //  1,
    private String servingUnit; //  tbsp,
    private Integer servingWeightGrams; //  14,
    private Integer metricQty; //  14,
    private String metricUom; //  g,
    private Float calories; //  100,
    private Float totalFat; //  12,
    private Float saturatedFat; //  8,
    private Float cholesterol; //  30,
    private Float sodium; //  0,
    private Float totalCarbohydrate; //  0,
    private Float dietaryFiber; //  null,
    private Float sugars; //  null,
    private Float protein; //  0,
    private Float potassium; //  null,
    private Float phosphorus; //  null,
    private Float calcium;
    private Float iron;
    private Float vitaminD;
    private Float addedSugars;
    private Float transFattyAcid;
    private String nixBrandName; //  Kerrygold,
    private String nixBrandId; //  51db37b7176fe9790a8989b4,
    private String nixItemId; //  52a15041d122497b50000a75,
    private PhotoDigest photo;
}
