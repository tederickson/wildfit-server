package com.wildfit.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IngredientType {
    MEAT("Meat & Seafood"),
    PRODUCE("Produce"),
    DAIRY("Eggs, Dairy & Cheese"),
    BREAD("Bread"),
    DELI("Deli & Specialty Cheese"),
    SPICE("Baking & Spices"),
    OIL("Oils, Sauces & Condiments"),
    INTERNATIONAL("International"),
    CAN("Canned & Jarred Goods"),
    NUT("Nut, Seeds & Dried Fruit"),
    NONE("");

    final String description;
}
