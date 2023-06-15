package com.wildfit.server.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchFoodResponse {
    private List<FoodItemDigest> common;
    private List<FoodItemDigest> branded;
}
