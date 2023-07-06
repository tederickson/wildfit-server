package com.wildfit.server.model;

import java.util.Arrays;

import com.wildfit.server.domain.UserStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Tied to {@link UserStatusType}.
 */
@Getter
@AllArgsConstructor
public enum UserStatus {
    FREE("F"), PREMIUM("P");

    final String code;

    public static UserStatus findByCode(String code) {
        return Arrays.stream(UserStatus.values()).filter(x -> x.code.equals(code)).findFirst().orElse(null);
    }
}
