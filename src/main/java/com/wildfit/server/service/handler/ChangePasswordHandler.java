package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import org.springframework.util.StringUtils;

@Builder(setterPrefix = "with")
public class ChangePasswordHandler {
    final UserRepository userRepository;
    final String userId;
    final String password;

    public void execute() throws UserServiceException {
        validate();

        if (PasswordValidator.isNotValid(password)) {
            throw new UserServiceException(UserServiceError.INVALID_PASSWORD);
        }
        final var encodedPassword = PasswordEncodeDecode.encode(password);

        final var user = userRepository.findByUuid(userId)
                                       .orElseThrow(() -> new UserServiceException(UserServiceError.USER_NOT_FOUND));

        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");

        if (userId == null) {
            throw new UserServiceException(UserServiceError.USER_NOT_FOUND);
        }
        if (!StringUtils.hasText(password)) {
            throw new UserServiceException(UserServiceError.INVALID_PASSWORD);
        }
    }
}
