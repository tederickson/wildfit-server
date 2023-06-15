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
    private Integer servingQty; //  1,
    private String servingUnit; //  tbsp,
    private Integer servingWeightGrams; //  14,
    private Integer metricQty; //  14,
    private String metricUom; //  g,
    private Integer nf_calories; //  100,
    private Integer nf_total_fat; //  12,
    private Integer nf_saturated_fat; //  8,
    private Integer nf_cholesterol; //  30,
    private Integer nf_sodium; //  0,
    private Integer nf_total_carbohydrate; //  0,
    private Integer nf_dietary_fiber; //  null,
    private Integer nf_sugars; //  null,
    private Integer nf_protein; //  0,
    private Integer nf_potassium; //  null,
    // private Integer nf_p; //  null,
    private String nix_brand_name; //  Kerrygold,
    private String nix_brand_id; //  51db37b7176fe9790a8989b4,
    private String nix_item_id; //  52a15041d122497b50000a75,
}
