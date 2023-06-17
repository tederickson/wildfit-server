package com.wildfit.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchedFoodItem {
    private String food_name; //  Butter, Pure Irish, Unsalted,
    private String brand_name; //  Kerrygold,
    private String brand_name_item_name; // Kerrygold Butter Sticks, Pure Irish, Unsalted
    private Integer serving_qty; //  1,
    private String serving_unit; //  tbsp,
    private Integer nf_calories; //  100,
    private String nix_brand_name; //  Kerrygold,
    private String nix_brand_id; //  51db37b7176fe9790a8989b4,
    private String nix_item_id; //  52a15041d122497b50000a75,
    private Photo photo;
    private Nutrient[] full_nutrients;
}
