package com.wildfit.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenderType {
    FEMALE("F"), MALE("M"), NON_BINARY("N");

    final String code;

    public static GenderType findByCode(String code) {
        for (var value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static GenderType findByCode(char code) {
        return findByCode(String.valueOf(code));
    }
}
