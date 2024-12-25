package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record FoodItemDigest(
        String foodName, //  Butter, Pure Irish, Unsalted,
        String brandName, //  Kerrygold,
        String brandNameItemName,
        Float servingQty, //  1,
        String servingUnit, //  tbsp,
        Float servingWeightGrams, //  14,
        Float metricQty, //  14,
        String metricUom, //  g,
        Float calories, //  100,
        Float totalFat, //  12,
        Float saturatedFat, //  8,
        Float cholesterol, //  30,
        Float sodium, //  0,
        Float totalCarbohydrate, //  0,
        Float dietaryFiber, //  null,
        Float sugars, //  null,
        Float protein, //  0,
        Float potassium, //  null,
        Float phosphorus, //  null,
        Float calcium,
        Float iron,
        Float vitaminD,
        Float addedSugars,
        Float transFattyAcid,
        String nixBrandName, //  Kerrygold,
        String nixBrandId, //  51db37b7176fe9790a8989b4,
        String nixItemId, //  52a15041d122497b50000a75,
        PhotoDigest photo) {
}
