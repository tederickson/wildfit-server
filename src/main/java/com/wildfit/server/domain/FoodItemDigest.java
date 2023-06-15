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
    private Integer calories; //  100,
    private Integer totalFat; //  12,
    private Integer saturatedFat; //  8,
    private Integer cholesterol; //  30,
    private Integer sodium; //  0,
    private Integer totalCarbohydrate; //  0,
    private Integer dietaryFiber; //  null,
    private Integer sugars; //  null,
    private Integer protein; //  0,
    private Integer potassium; //  null,
    // private Integer nf_p; //  null,
    private String nixBrandName; //  Kerrygold,
    private String nixBrandId; //  51db37b7176fe9790a8989b4,
    private String nixItemId; //  52a15041d122497b50000a75,
}
