package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;

@Builder(setterPrefix = "with")
public class DeleteUserHandler {

    final UserRepository userRepository;
    final String userId;

    public void execute() throws WildfitServiceException {
        validate();

        final var user = userRepository.findByUuid(userId)
                                       .orElseThrow(() -> new WildfitServiceException(
                                               WildfitServiceError.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    private void validate() throws WildfitServiceException {
        Objects.requireNonNull(userRepository, "userRepository");

        if (userId == null) {
            throw new WildfitServiceException(WildfitServiceError.USER_NOT_FOUND);
        }
    }
}

