package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record RecipeSummaryDigest(
        Long id,

        String name,
        SeasonType season,

        int prepTimeMin,
        int cookTimeMin,

        String servingUnit,
        int servingQty,

        String introduction,
        String thumbnail) {
}
