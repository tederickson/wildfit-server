package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.mapper.UserProfileDigestMapper;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;

@Builder(setterPrefix = "with")
public class GetUserProfileHandler {

    final UserRepository userRepository;
    final UserProfileRepository userProfileRepository;
    final String userId;

    public UserProfileDigest execute() throws WildfitServiceException {
        validate();

        final var user = userRepository.findByUuid(userId)
                                       .orElseThrow(
                                               () -> new WildfitServiceException(WildfitServiceError.USER_NOT_FOUND));

        final var userProfile = userProfileRepository.findByUser(user).orElse(null);

        return UserProfileDigestMapper.map(user, userProfile);
    }

    private void validate() throws WildfitServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(userProfileRepository, "userProfileRepository");

        if (userId == null) {
            throw new WildfitServiceException(WildfitServiceError.USER_NOT_FOUND);
        }
    }
}

