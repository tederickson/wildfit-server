package com.wildfit.server.model;

import com.wildfit.server.domain.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    FEMALE("F"), MALE("M"), NON_BINARY("N");

    private final String code;

    public static Gender findByCode(String code) {
        for (var value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static Gender findByCode(char code) {
        return findByCode(String.valueOf(code));
    }

    public static Gender map(GenderType genderType) {
        return valueOf(genderType.name());
    }

    public char getCodeAsCharacter() {
        return code.charAt(0);
    }

    public GenderType toGenderType() {
        return GenderType.valueOf(name());
    }
}
