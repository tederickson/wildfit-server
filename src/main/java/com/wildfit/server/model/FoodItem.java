package com.wildfit.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodItem {
    private String food_name; //  Butter, Pure Irish, Unsalted,
    private String brand_name; //  Kerrygold,
    private String brand_name_item_name; // Kerrygold Butter Sticks, Pure Irish, Unsalted
    private Integer serving_qty; //  1,
    private String serving_unit; //  tbsp,
    private Integer serving_weight_grams; //  14,
    private Integer nf_metric_qty; //  14,
    private String nf_metric_uom; //  g,
    private Float nf_calories; //  100,
    private Float nf_total_fat; //  12,
    private Float nf_saturated_fat; //  8,
    private Float nf_cholesterol; //  30,
    private Float nf_sodium; //  0,
    private Float nf_total_carbohydrate; //  0,
    private Float nf_dietary_fiber; //  null,
    private Float nf_sugars; //  null,
    private Float nf_protein; //  0,
    private Float nf_potassium; //  null,
    private Float nf_p; //  null,
    private String nix_brand_name; //  Kerrygold,
    private String nix_brand_id; //  51db37b7176fe9790a8989b4,
    private String nix_item_id; //  52a15041d122497b50000a75,
    private Photo photo;
    private Nutrient[] full_nutrients;
}
