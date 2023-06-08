package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.model.User;


public final class UserDigestMapper {
    private UserDigestMapper() {
    }

    public static UserDigest map(User user) {
        return UserDigest.builder()
                .withEmail(user.getEmail())
                .build();
    }
}
