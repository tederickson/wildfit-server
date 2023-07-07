package com.wildfit.server.model;

import com.wildfit.server.domain.UserStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    FREE("F"), PREMIUM("P");

    final String code;

    public static UserStatus findByCode(String code) {
        for (var value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static UserStatus map(UserStatusType userStatusType) {
        return valueOf(userStatusType.name());
    }

    public UserStatusType toUserStatusType() {
        return UserStatusType.valueOf(name());
    }
}
