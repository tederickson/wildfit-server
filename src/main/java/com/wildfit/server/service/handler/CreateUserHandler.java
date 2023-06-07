package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.mapper.UserDigestMapper;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Builder(setterPrefix = "with")
public class CreateUserHandler {

    final UserRepository userRepository;
    final UserDigest userDigest;

    public UserDigest execute() throws UserServiceException {
        validate();

        if (!PasswordValidator.isValid(userDigest.getPassword())) {
            throw new UserServiceException("invalid password");
        }
        final var encodedPassword = PasswordEncodeDecode.encode(userDigest.getPassword());
        final var users = userRepository.findByUserName(userDigest.getUserName());

        if (!CollectionUtils.isEmpty(users)) {
            throw new UserServiceException("user " + userDigest.getUserName() + " already exists");
        }
        final var user = User.builder()
                .withUserName(userDigest.getUserName())
                .withPassword(encodedPassword)
                .withEmail(userDigest.getEmail()).build();
        final var saved = userRepository.save(user);
        final var mapper = new UserDigestMapper();

        return mapper.map(saved);
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(userDigest, "userDigest");

        if (!StringUtils.hasText(userDigest.getUserName())) {
            throw new UserServiceException("missing user name");
        }
        if (!StringUtils.hasText(userDigest.getPassword())) {
            throw new UserServiceException("missing password");
        }
    }
}

