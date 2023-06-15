package com.wildfit.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchedFoodItems {
    private FoodItem[] common;
    private FoodItem[] branded;
}
