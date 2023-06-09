package com.wildfit.server.service.handler;

import java.util.Date;
import java.util.Objects;

import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserProfile;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.model.mapper.CreateUserResponseMapper;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Builder(setterPrefix = "with")
public class CreateUserHandler {

    final UserRepository userRepository;
    final UserProfileRepository userProfileRepository;
    final UserDigest userDigest;

    public CreateUserResponse execute() throws UserServiceException {
        validate();
        log.info(userDigest.toString());

        if (!PasswordValidator.isValid(userDigest.getPassword())) {
            throw new UserServiceException(UserServiceError.INVALID_PASSWORD);
        }
        final var encodedPassword = PasswordEncodeDecode.encode(userDigest.getPassword());
        final var users = userRepository.findByEmail(userDigest.getEmail());

        if (!CollectionUtils.isEmpty(users)) {
            throw new UserServiceException(UserServiceError.EXISTING_USER);
        }

        final var user = User.builder()
                .withStatus(UserStatus.CREATE.getCode())
                .withCreateDate(new Date())
                .withPassword(encodedPassword)
                .withEmail(userDigest.getEmail()).build();
        final var userProfile = UserProfile.builder().withUser(user).build();

        final var saved = userProfileRepository.save(userProfile);

        return CreateUserResponseMapper.map(saved.getUser());
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

