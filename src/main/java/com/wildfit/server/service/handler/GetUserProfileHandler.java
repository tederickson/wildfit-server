package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.mapper.UserProfileDigestMapper;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

@Builder(setterPrefix = "with")
@Slf4j
public class GetUserProfileHandler {

    final UserRepository userRepository;
    final UserProfileRepository userProfileRepository;
    final Long userId;

    public UserProfileDigest execute() throws UserServiceException {
        log.info("GetUserProfileHandler(" + userId + ")");
        validate();

        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException(UserServiceError.USER_NOT_FOUND));

        final var userProfiles = userProfileRepository.findByUser(user);
        if (CollectionUtils.isEmpty(userProfiles)) {
            throw new UserServiceException(UserServiceError.USER_NOT_FOUND);
        }

        return UserProfileDigestMapper.map(userProfiles.get(0));
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(userProfileRepository, "userProfileRepository");

        if (userId == null) {
            throw new UserServiceException(UserServiceError.USER_NOT_FOUND);
        }
    }
}

