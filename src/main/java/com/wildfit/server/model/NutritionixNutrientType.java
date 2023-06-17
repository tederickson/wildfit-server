package com.wildfit.server.model;

import lombok.Getter;

@Getter
public enum NutritionixNutrientType {
    PROCNT(203, "PROCNT", "Protein", "g", "nf_protein"),
    FAT(204, "FAT", "Total lipid (fat)", "g", "nf_total_fat"),
    CHOCDF(205, "CHOCDF", "Carbohydrate, by difference", "g", "nf_total_carbohydrate"),
    ENERC_KCAL(208, "ENERC_KCAL", "Energy", "kcal", "nf_calories"),
    SUGAR(269, "SUGAR", "Sugars, total", "g", "nf_sugars"),
    FIBTG(291, "FIBTG", "Fiber, total dietary", "g", "nf_dietary_fiber"),
    CA(301, "CA", "Calcium, Ca", "mg", "nf_calcium_mg"),
    FE(303, "FE", "Iron, Fe", "mg", "nf_iron_mg"),
    P(305, "P", "Phosphorus, P", "mg", "nf_p"),
    K(306, "K", "Potassium, K", "mg", "nf_potassium"),
    NA(307, "NA", "Sodium, Na", "mg", "nf_sodium"),
    VITD(324, "VITD", "Vitamin D", "IU", "nf_vitamin_d_mcg"),
    SUGAR_ADD(539, "SUGAR_ADD", "Sugars, added", "g", "nf_added_sugars"),
    CHOLE(601, "CHOLE", "Cholesterol", "mg", "nf_cholesterol"),
    FATRN(605, "FATRN", "Fatty acids, total trans", "g", "nf_trans_fatty_acid"),
    FASAT(606, "FASAT", "Fatty acids, total saturated", "g", "nf_saturated_fat");

    private final int attrId;
    private final String usdaTag;
    private final String description;
    private final String unit;
    private final String bulkCsvField;

    NutritionixNutrientType(int attrId, String usdaTag, String description, String unit, String bulkCsvField) {
        this.attrId = attrId;
        this.usdaTag = usdaTag;
        this.description = description;
        this.unit = unit;
        this.bulkCsvField = bulkCsvField;
    }
}
