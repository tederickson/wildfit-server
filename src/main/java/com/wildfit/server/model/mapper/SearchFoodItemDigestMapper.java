package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.SearchFoodItemDigest;
import com.wildfit.server.model.NutritionixNutrientType;
import com.wildfit.server.model.SearchedFoodItem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SearchFoodItemDigestMapper {
    private SearchFoodItemDigestMapper() {
    }

    public static SearchFoodItemDigest map(SearchedFoodItem food) {
        final var builder = SearchFoodItemDigest.builder();

        if (food != null) {
            builder.withFoodName(food.getFood_name())
                    .withBrandName(food.getBrand_name())
                    .withBrandNameItemName(food.getBrand_name_item_name())
                    .withServingQty(food.getServing_qty())
                    .withServingUnit(food.getServing_unit())
                    .withNixBrandName(food.getNix_brand_name())
                    .withNixBrandId(food.getNix_brand_id())
                    .withNixItemId(food.getNix_item_id())
                    .withPhoto(PhotoDigestMapper.map(food.getPhoto()));

            if (food.getFull_nutrients() != null) {
                for (var nutrient : food.getFull_nutrients()) {
                    final var nutrientType = NutritionixNutrientType.findByAttrId(nutrient.getAttr_id());

                    if (nutrientType != null) {
                        switch (nutrientType) {
                            //    PROCNT(203, "PROCNT", "Protein", "g", "nf_protein"),
                            case PROCNT -> builder.withProtein(nutrient.getValue());

                            //    FAT(204, "FAT", "Total lipid (fat)", "g", "nf_total_fat"),
                            case FAT -> builder.withTotalFat(nutrient.getValue());

                            //    CHOCDF(205, "CHOCDF", "Carbohydrate, by difference", "g", "nf_total_carbohydrate"),
                            case CHOCDF -> builder.withTotalCarbohydrate(nutrient.getValue());

                            //    ENERC_KCAL(208, "ENERC_KCAL", "Energy", "kcal", "nf_calories"),
                            case ENERC_KCAL -> builder.withCalories(nutrient.getValue());

                            //    SUGAR(269, "SUGAR", "Sugars, total", "g", "nf_sugars"),
                            case SUGAR -> builder.withSugars(nutrient.getValue());

                            //    FIBTG(291, "FIBTG", "Fiber, total dietary", "g", "nf_dietary_fiber"),
                            case FIBTG -> builder.withDietaryFiber(nutrient.getValue());

                            //    CA(301, "CA", "Calcium, Ca", "mg", "nf_calcium_mg"),
                            case CA -> builder.withCalcium(nutrient.getValue());

                            //    FE(303, "FE", "Iron, Fe", "mg", "nf_iron_mg"),
                            case FE -> builder.withIron(nutrient.getValue());

                            //    P(305, "P", "Phosphorus, P", "mg", "nf_p"),
                            case P -> builder.withPhosphorus(nutrient.getValue());

                            //    K(306, "K", "Potassium, K", "mg", "nf_potassium"),
                            case K -> builder.withPotassium(nutrient.getValue());

                            //    NA(307, "NA", "Sodium, Na", "mg", "nf_sodium"),
                            case NA -> builder.withSodium(nutrient.getValue());

                            //    VITD(324, "VITD", "Vitamin D", "IU", "nf_vitamin_d_mcg"),
                            case VITD -> builder.withVitaminD(nutrient.getValue());

                            //    SUGAR_ADD(539, "SUGAR_ADD", "Sugars, added", "g", "nf_added_sugars"),
                            case SUGAR_ADD -> builder.withAddedSugars(nutrient.getValue());

                            //    CHOLE(601, "CHOLE", "Cholesterol", "mg", "nf_cholesterol"),
                            case CHOLE -> builder.withCholesterol(nutrient.getValue());

                            //    FATRN(605, "FATRN", "Fatty acids, total trans", "g", "nf_trans_fatty_acid"),
                            case FATRN -> builder.withTransFattyAcid(nutrient.getValue());

                            //    FASAT(606, "FASAT", "Fatty acids, total saturated", "g", "nf_saturated_fat");
                            case FASAT -> builder.withSaturatedFat(nutrient.getValue());
                            default -> log.warn("Unexpected nutrient type: " + nutrientType);
                        }
                    }
                }
            }
        }
        return builder.build();
    }
}
