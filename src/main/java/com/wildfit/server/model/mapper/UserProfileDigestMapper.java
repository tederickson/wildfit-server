package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.model.Gender;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserProfile;

public final class UserProfileDigestMapper {
    private UserProfileDigestMapper() {
    }

    public static UserProfileDigest map(User user, UserProfile userProfile) {
        final var userDigest = UserDigestMapper.map(user);
        final var builder = UserProfileDigest.builder()
                .withUser(userDigest);

        if (userProfile != null) {
            builder.withName(userProfile.getName())
                    .withAge(userProfile.getAge())
                    .withHeightFeet(userProfile.getHeight_feet())
                    .withHeightInches(userProfile.getHeight_inches())
                    .withWeight(userProfile.getWeight());

            final var gender = Gender.findByCode(userProfile.getGender());
            if (gender != null) {
                builder.withGender(gender.toGenderType());
            }
        }

        return builder.build();
    }
}
