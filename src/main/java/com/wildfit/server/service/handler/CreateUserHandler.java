package com.wildfit.server.service.handler;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.domain.mapper.UserDigestMapper;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

@Builder(setterPrefix = "with")
public class CreateUserHandler {
    @Autowired
    UserRepository userRepository;

    final UserDigest userDigest;

    public UserDigest execute() throws UserServiceException {
        if (PasswordValidator.isValid(userDigest.getPassword())) {
            final var encodedPassword = PasswordEncodeDecode.encode(userDigest.getPassword());
            final var users = userRepository.findByUserName(userDigest.getUserName());

            if (CollectionUtils.isEmpty(users)) {
                final var user = User.builder()
                        .withUserName(userDigest.getUserName())
                        .withPassword(encodedPassword)
                        .withEmail(userDigest.getEmail()).build();
                final var saved = userRepository.save(user);
                final var mapper = new UserDigestMapper();

                return mapper.map(saved);
            }
            throw new UserServiceException("user " + userDigest.getUserName() + " already exists");
        }
        throw new UserServiceException("invalid password");
    }

}
