package com.wildfit.server.service.handler;


import java.util.Objects;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.mapper.UserDigestMapper;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Builder(setterPrefix = "with")
@Slf4j
public class LoginHandler {
    final UserRepository userRepository;
    final String email;
    final String password;

    public UserDigest execute() throws UserServiceException {
        log.info("LoginHandler(" + email + "," + password + ")");
        validate();

        final var users = userRepository.findByEmail(email);
        if (!CollectionUtils.isEmpty(users)) {
            final var user = users.get(0);

            if (!user.isEnabled()) {
                throw new UserServiceException(UserServiceError.NOT_REGISTERED);
            }
            if (PasswordEncodeDecode.matches(password, user.getPassword())) {
                return UserDigestMapper.map(user);
            }
        }

        throw new UserServiceException(UserServiceError.USER_NOT_FOUND);
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");

        if (!StringUtils.hasText(email)) {
            throw new UserServiceException(UserServiceError.MISSING_EMAIL);
        }
        if (!StringUtils.hasText(password)) {
            throw new UserServiceException(UserServiceError.INVALID_PASSWORD);
        }
    }
}
