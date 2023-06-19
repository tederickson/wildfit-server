package com.wildfit.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDigest {
    private long id;

    private String name;
    private SeasonType season;

    private int prepTimeMin;
    private int cookTimeMin;

    private String servingUnit;
    private int servingQty;

    private String introduction;
    private String instructions;
}
