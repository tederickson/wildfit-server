package com.wildfit.server.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    private String food_name; //  Butter, Pure Irish, Unsalted,
    private String brand_name; //  Kerrygold,
    private String nix_brand_id; //  51db37b7176fe9790a8989b4,
    private String nix_item_id; //  52a15041d122497b50000a75,
    private String brand_name_item_name; // Kerrygold Butter Sticks, Pure Irish, Unsalted
    private Integer serving_qty; //  1,
    private String serving_unit; //  tbsp,
    private Integer serving_weight_grams; //  14,
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

    private String photo_thumbnail;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        com.wildfit.server.model.RecipeIngredient that = (com.wildfit.server.model.RecipeIngredient) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
