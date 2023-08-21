package com.wildfit.server.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class MealDigest {
    private Long id;
    private String uuid;

    private LocalDate startDate;
    private LocalDate endDate;

    private List<MealSummaryDigest> recipes;
}
