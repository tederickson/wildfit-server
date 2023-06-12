package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.repository.VerificationTokenRepository;
import lombok.Builder;
import org.springframework.util.StringUtils;

@Builder(setterPrefix = "with")
public class ConfirmUserHandler {
    final UserRepository userRepository;
    final VerificationTokenRepository verificationTokenRepository;
    final String email;
    final String confirmationCode;

    public void execute() throws UserServiceException {
        validate();

        final var verificationToken = verificationTokenRepository.findByToken(confirmationCode);
        if (verificationToken == null) {
            throw new UserServiceException(UserServiceError.INVALID_CONFIRMATION_CODE);
        }

        final var user = verificationToken.getUser();
        if (email.equals(user.getEmail())) {
            user.setStatus(UserStatus.FREE.getCode());
            user.setEnabled(true);

            userRepository.save(user);
        } else {
            throw new UserServiceException(UserServiceError.INVALID_CONFIRMATION_CODE);
        }
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(verificationTokenRepository, "verificationTokenRepository");

        if (!StringUtils.hasText(email)) {
            throw new UserServiceException(UserServiceError.MISSING_EMAIL);
        }
        if (!StringUtils.hasText(confirmationCode)) {
            throw new UserServiceException(UserServiceError.INVALID_CONFIRMATION_CODE);
        }
    }
}
