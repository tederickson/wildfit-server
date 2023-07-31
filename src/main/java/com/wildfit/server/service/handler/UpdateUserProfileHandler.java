package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.UpdateUserProfileRequest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.Gender;
import com.wildfit.server.model.UserProfile;
import com.wildfit.server.model.mapper.UserProfileDigestMapper;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
@Builder(setterPrefix = "with")
public class UpdateUserProfileHandler {
    final UserRepository userRepository;
    final UserProfileRepository userProfileRepository;
    final String userId;
    final UpdateUserProfileRequest userProfileRequest;

    public UserProfileDigest execute() throws UserServiceException {
        validate();
        log.info(userId + "|" + userProfileRequest);

        final var user = userRepository.findByUuid(userId).orElseThrow(() ->
                new UserServiceException(UserServiceError.USER_NOT_FOUND));

        final var userProfile = userProfileRepository.findByUser(user)
                                                     .orElse(UserProfile.builder().withUser(user).build());

        userProfile.setName(userProfileRequest.getName());
        userProfile.setAge(userProfileRequest.getAge());

        if (userProfileRequest.getGender() != null) {
            final var gender = Gender.map(userProfileRequest.getGender());
            userProfile.setGender(gender.getCodeAsCharacter());
        }

        userProfile.setHeight_feet(userProfileRequest.getHeightFeet());
        userProfile.setHeight_inches(userProfileRequest.getHeightInches());
        userProfile.setWeight(userProfileRequest.getWeight());

        final var saved = userProfileRepository.save(userProfile);

        return UserProfileDigestMapper.map(user, saved);
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(userProfileRepository, "userProfileRepository");
        Objects.requireNonNull(userId, "userId");
        Objects.requireNonNull(userProfileRequest, "userProfileRequest");

        if (!StringUtils.hasText(userProfileRequest.getName())) {
            throw new UserServiceException(UserServiceError.INVALID_NAME);
        }
    }
}
