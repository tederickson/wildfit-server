package com.wildfit.server.service.handler;

import java.util.Date;
import java.util.Objects;

import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserProfile;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.model.VerificationToken;
import com.wildfit.server.model.mapper.CreateUserResponseMapper;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.repository.VerificationTokenRepository;
import lombok.Builder;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Builder(setterPrefix = "with")
public class CreateUserHandler {

    final UserRepository userRepository;
    final UserProfileRepository userProfileRepository;
    final VerificationTokenRepository verificationTokenRepository;
    final String email;
    final String password;

    public CreateUserResponse execute() throws UserServiceException {
        validate();

        if (!PasswordValidator.isValid(password)) {
            throw new UserServiceException(UserServiceError.INVALID_PASSWORD);
        }
        final var encodedPassword = PasswordEncodeDecode.encode(password);
        final var users = userRepository.findByEmail(email);

        if (!CollectionUtils.isEmpty(users)) {
            throw new UserServiceException(UserServiceError.EXISTING_USER);
        }

        final var user = User.builder()
                .withStatus(UserStatus.CREATE.getCode())
                .withCreateDate(new Date())
                .withPassword(encodedPassword)
                .withEmail(email)
                .withEnabled(false)
                .build();
        final var userProfile = UserProfile.builder().withUser(user).build();

        final var saved = userProfileRepository.save(userProfile);

        final var verificationToken = new VerificationToken(RandomStringUtils.randomAlphabetic(20),
                userProfile.getUser());

        verificationTokenRepository.save(verificationToken);

        return CreateUserResponseMapper.map(saved.getUser());
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(userProfileRepository, "userProfileRepository");
        Objects.requireNonNull(verificationTokenRepository, "verificationTokenRepository");

        if (!StringUtils.hasText(email)) {
            throw new UserServiceException(UserServiceError.MISSING_EMAIL);
        }
        if (!StringUtils.hasText(password)) {
            throw new UserServiceException(UserServiceError.INVALID_PASSWORD);
        }
    }
}

