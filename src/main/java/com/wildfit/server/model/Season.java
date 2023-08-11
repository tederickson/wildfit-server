package com.wildfit.server.model;

import com.wildfit.server.domain.SeasonType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Season {
    SPRING("Spring"), SUMMER("Summer"), FALL("Fall"), WINTER("Winter");

    private final String code;  // TODO: Yank code

    public static Season findByCode(String code) {
        for (var value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static Season map(SeasonType genderType) {
        return valueOf(genderType.name());
    }

    public SeasonType toSeasonType() {
        return SeasonType.valueOf(name());
    }

}
