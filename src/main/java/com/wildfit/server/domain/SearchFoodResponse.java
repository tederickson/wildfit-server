package com.wildfit.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchFoodResponse {
    private List<FoodItemDigest> common;
    private List<FoodItemDigest> branded;
}
