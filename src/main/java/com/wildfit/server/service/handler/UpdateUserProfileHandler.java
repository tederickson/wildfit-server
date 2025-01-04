package com.wildfit.server.service.handler;

import com.wildfit.server.domain.UpdateUserProfileRequest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Gender;
import com.wildfit.server.model.UserProfile;
import com.wildfit.server.model.mapper.UserProfileDigestMapper;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Builder(setterPrefix = "with")
public class UpdateUserProfileHandler {
    final UserRepository userRepository;
    final UserProfileRepository userProfileRepository;
    final String userId;
    final UpdateUserProfileRequest userProfileRequest;

    public UserProfileDigest execute() throws WildfitServiceException {
        validate();

        final var user = userRepository.findByUuid(userId).orElseThrow(() ->
                                                                               new WildfitServiceException(WildfitServiceError.USER_NOT_FOUND));

        final var userProfile = userProfileRepository.findByUser(user)
                .orElse(UserProfile.builder().withUser(user).build());

        userProfile.setName(userProfileRequest.name());
        userProfile.setAge(userProfileRequest.age());

        if (userProfileRequest.gender() != null) {
            final var gender = Gender.map(userProfileRequest.gender());
            userProfile.setGender(gender.getCodeAsCharacter());
        }

        userProfile.setHeight_feet(userProfileRequest.heightFeet());
        userProfile.setHeight_inches(userProfileRequest.heightInches());
        userProfile.setWeight(userProfileRequest.weight());

        final var saved = userProfileRepository.save(userProfile);

        return UserProfileDigestMapper.map(user, saved);
    }

    private void validate() throws WildfitServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(userProfileRepository, "userProfileRepository");
        Objects.requireNonNull(userId, "userId");
        Objects.requireNonNull(userProfileRequest, "userProfileRequest");

        if (!StringUtils.hasText(userProfileRequest.name())) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_NAME);
        }
    }
}
