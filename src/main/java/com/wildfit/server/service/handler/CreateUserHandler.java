package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserProfile;
import com.wildfit.server.model.mapper.UserDigestMapper;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Builder(setterPrefix = "with")
public class CreateUserHandler {

    final UserRepository userRepository;
    final UserProfileRepository userProfileRepository;
    final UserDigest userDigest;

    public UserDigest execute() throws UserServiceException {
        validate();

        if (!PasswordValidator.isValid(userDigest.getPassword())) {
            throw new UserServiceException(UserServiceError.INVALID_PASSWORD);
        }
        final var encodedPassword = PasswordEncodeDecode.encode(userDigest.getPassword());
        final var userName = userDigest.getEmail();
        final var users = userRepository.findByUserName(userName);

        if (!CollectionUtils.isEmpty(users)) {
            throw new UserServiceException(UserServiceError.EXISTING_USER);
        }
        final var user = User.builder()
                .withUserName(userName)
                .withPassword(encodedPassword)
                .withEmail(userDigest.getEmail()).build();
        final var userProfile = UserProfile.builder().withUser(user).build();

        userProfileRepository.save(userProfile);

        return UserDigestMapper.map(user);
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(userProfileRepository, "userProfileRepository");
        Objects.requireNonNull(userDigest, "userDigest");

        if (!StringUtils.hasText(userDigest.getEmail())) {
            throw new UserServiceException(UserServiceError.MISSING_EMAIL);
        }
        if (!StringUtils.hasText(userDigest.getPassword())) {
            throw new UserServiceException(UserServiceError.INVALID_PASSWORD);
        }
    }
}

