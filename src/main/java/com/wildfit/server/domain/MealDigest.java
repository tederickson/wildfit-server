package com.wildfit.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
