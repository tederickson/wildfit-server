package com.wildfit.server.model;

import com.wildfit.server.domain.SeasonType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Season {
    SPRING, SUMMER, FALL;

    public static Season map(SeasonType genderType) {
        return valueOf(genderType.name());
    }

    public SeasonType toSeasonType() {
        return SeasonType.valueOf(name());
    }

}
