package com.wildfit.server.domain.mapper;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.model.User;


public final class UserDigestMapper {

    public UserDigest map(User user) {
        return UserDigest.builder()
                .withUserName(user.getUserName())
                .withEmail(user.getEmail())
                .build();
    }
}
