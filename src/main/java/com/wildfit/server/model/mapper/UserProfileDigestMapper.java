package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.model.UserProfile;

public final class UserProfileDigestMapper {
    private UserProfileDigestMapper() {
    }

    public static UserProfileDigest map(UserProfile userProfile) {
        final var user = userProfile.getUser();
        final var userDigest = UserDigest.builder()
                .withUserName(user.getUserName())
                .withEmail(user.getEmail())
                .build();

        return UserProfileDigest.builder().withUser(userDigest)
                .withAge(userProfile.getAge())
                .withGender(userProfile.getGender())
                .withHeight(userProfile.getHeight())
                .withWeight(userProfile.getWeight())
                .build();
    }
}
