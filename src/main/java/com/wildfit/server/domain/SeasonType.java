package com.wildfit.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SeasonType {
    SPRING("Spring"), SUMMER("Summer"), FALL("Fall"), WINTER("Winter");

    private final String code;

    public static SeasonType findByCode(String code) {
        for (var value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
