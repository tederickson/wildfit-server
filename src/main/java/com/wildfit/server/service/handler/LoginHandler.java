package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.mapper.UserDigestMapper;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Builder(setterPrefix = "with")
public class LoginHandler {
    final UserRepository userRepository;
    final String email;
    final String password;

    public UserDigest execute() throws WildfitServiceException {
        validate();

        final var users = userRepository.findByEmail(email);
        if (!CollectionUtils.isEmpty(users)) {
            final var user = users.get(0);

            if (!user.isEnabled()) {
                throw new WildfitServiceException(WildfitServiceError.NOT_REGISTERED);
            }
            if (PasswordEncodeDecode.matches(password, user.getPassword())) {
                return UserDigestMapper.map(user);
            }
        }

        throw new WildfitServiceException(WildfitServiceError.USER_NOT_FOUND);
    }

    private void validate() throws WildfitServiceException {
        Objects.requireNonNull(userRepository, "userRepository");

        if (!StringUtils.hasText(email)) {
            throw new WildfitServiceException(WildfitServiceError.MISSING_EMAIL);
        }
        if (!StringUtils.hasText(password)) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PASSWORD);
        }
    }
}
