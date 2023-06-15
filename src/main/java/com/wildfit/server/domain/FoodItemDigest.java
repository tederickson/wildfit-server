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
    private String food_name; //  Butter, Pure Irish, Unsalted,
    private String brand_name; //  Kerrygold,
    private Integer serving_qty; //  1,
    private String serving_unit; //  tbsp,
    private Integer serving_weight_grams; //  14,
    private Integer nf_metric_qty; //  14,
    private String nf_metric_uom; //  g,
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
    private Integer nf_p; //  null,
    private String nix_brand_name; //  Kerrygold,
    private String nix_brand_id; //  51db37b7176fe9790a8989b4,
    private String nix_item_id; //  52a15041d122497b50000a75,
}
