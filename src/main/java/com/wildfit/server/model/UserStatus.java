package com.wildfit.server.model;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    CREATE("C"), FREE("F"), PREMIUM("P");

    final String code;

    public static UserStatus findByCode(String code) {
        return Arrays.stream(UserStatus.values()).filter(x -> x.code.equals(code)).findFirst().orElse(null);
    }
}
