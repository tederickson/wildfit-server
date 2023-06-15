package com.wildfit.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchFoodResponse {
    private com.wildfit.server.model.FoodItem[] common;
    private com.wildfit.server.model.FoodItem[] branded;
}
