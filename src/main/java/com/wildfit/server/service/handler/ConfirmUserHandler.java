package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.RegisterUserResponse;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.repository.VerificationTokenRepository;
import lombok.Builder;
import org.springframework.util.StringUtils;

@Builder(setterPrefix = "with")
public class ConfirmUserHandler {
    final UserRepository userRepository;
    final VerificationTokenRepository verificationTokenRepository;
    final String confirmationCode;

    public RegisterUserResponse execute() throws WildfitServiceException {
        validate();

        final var verificationToken = verificationTokenRepository.findByToken(confirmationCode);
        if (verificationToken == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_CONFIRMATION_CODE);
        }

        final var user = verificationToken.getUser();

        user.setEnabled(true);

        userRepository.save(user);

        return RegisterUserResponse.builder()
                                   .withMessage("Your account is active.")
                                   .build();
    }

    private void validate() throws WildfitServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(verificationTokenRepository, "verificationTokenRepository");

        if (!StringUtils.hasText(confirmationCode)) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_CONFIRMATION_CODE);
        }
    }
}
