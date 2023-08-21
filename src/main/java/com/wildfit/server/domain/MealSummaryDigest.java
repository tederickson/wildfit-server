package com.wildfit.server.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class MealSummaryDigest {
    private Long id;
    private Long recipeId;

    private String name;
    private SeasonType season;
    private LocalDate planDate;
    private boolean cooked;
    private String thumbnail;
}
