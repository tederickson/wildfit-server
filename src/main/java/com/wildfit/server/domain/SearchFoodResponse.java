package com.wildfit.server.domain;

import java.util.List;

public record SearchFoodResponse(List<FoodItemDigest> common, List<FoodItemDigest> branded) {}
