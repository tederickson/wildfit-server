package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.domain.UserStatusType;
import com.wildfit.server.model.User;

import java.util.Optional;

public final class UserDigestMapper {
    private UserDigestMapper() {
    }

    public static UserDigest map(User user) {
        final var status = Optional.of(user)
                .map(User::getUserStatus)
                .map(Enum::name)
                .map(UserStatusType::valueOf)
                .orElse(null);

        return UserDigest.builder()
                .withId(user.getId())
                .withEmail(user.getEmail())
                .withStatus(status)
                .withUuid(user.getUuid())
                .build();
    }
}
