package com.wildfit.server.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class RecipeDigest {
    private Long id;

    private String name;
    private SeasonType season;

    private int prepTimeMin;
    private int cookTimeMin;

    private String servingUnit;
    private int servingQty;

    private String introduction;
    private List<RecipeGroupDigest> recipeGroups;
    private String thumbnail;
}
