package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import org.springframework.util.StringUtils;

@Builder(setterPrefix = "with")
public class ChangePasswordHandler {
    final UserRepository userRepository;
    final String userId;
    final String password;

    public void execute() throws WildfitServiceException {
        validate();

        if (PasswordValidator.isNotValid(password)) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PASSWORD);
        }
        final var encodedPassword = PasswordEncodeDecode.encode(password);

        final var user = userRepository.findByUuid(userId)
                                       .orElseThrow(() -> new WildfitServiceException(
                                               WildfitServiceError.USER_NOT_FOUND));

        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    private void validate() throws WildfitServiceException {
        Objects.requireNonNull(userRepository, "userRepository");

        if (userId == null) {
            throw new WildfitServiceException(WildfitServiceError.USER_NOT_FOUND);
        }
        if (!StringUtils.hasText(password)) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PASSWORD);
        }
    }
}
