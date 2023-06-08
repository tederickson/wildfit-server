package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.mapper.UserProfileDigestMapper;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Builder(setterPrefix = "with")
@Slf4j
public class GetUserProfileHandler {

    final UserRepository userRepository;
    final UserProfileRepository userProfileRepository;
    final UserDigest userDigest;

    public UserProfileDigest execute() throws UserServiceException {
        log.info("GetUserProfileHandler(" + userDigest + ")");
        validate();

        final var users = userRepository.findByUserName(userDigest.getUserName());
        if (CollectionUtils.isEmpty(users)) {
            throw new UserServiceException(UserServiceError.USER_NOT_FOUND);
        }

        final var userProfiles = userProfileRepository.findByUser(users.get(0));
        if (CollectionUtils.isEmpty(userProfiles)) {
            throw new UserServiceException(UserServiceError.USER_NOT_FOUND);
        }

        return UserProfileDigestMapper.map(userProfiles.get(0));
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(userProfileRepository, "userProfileRepository");
        Objects.requireNonNull(userDigest, "userDigest");

        if (!StringUtils.hasText(userDigest.getUserName())) {
            throw new UserServiceException(UserServiceError.MISSING_USER_NAME);
        }

    }
}

