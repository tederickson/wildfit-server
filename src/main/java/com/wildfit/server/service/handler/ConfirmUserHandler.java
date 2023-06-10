package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Builder(setterPrefix = "with")
public class ConfirmUserHandler {
    final UserRepository userRepository;
    final String email;
    final String confirmationCode;

    public void execute() throws UserServiceException {
        validate();

        final var users = userRepository.findByEmail(email);
        if (CollectionUtils.isEmpty(users)) {
            throw new UserServiceException(UserServiceError.USER_NOT_FOUND);
        }

        final var user = users.get(0);
        if (confirmationCode.equals(user.getConfirmCode())) {
            user.setStatus(UserStatus.FREE.getCode());
            userRepository.save(user);
        } else {
            throw new UserServiceException(UserServiceError.INVALID_CONFIRMATION_CODE);
        }
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");

        if (!StringUtils.hasText(email)) {
            throw new UserServiceException(UserServiceError.MISSING_EMAIL);
        }
        if (!StringUtils.hasText(confirmationCode)) {
            throw new UserServiceException(UserServiceError.INVALID_CONFIRMATION_CODE);
        }
    }
}
