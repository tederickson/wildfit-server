package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;

@Builder(setterPrefix = "with")
public class DeleteUserHandler {

    final UserRepository userRepository;
    final Long userId;

    public void execute() throws UserServiceException {
        validate();

        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new UserServiceException(UserServiceError.USER_NOT_FOUND);
        }
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");

        if (userId == null) {
            throw new UserServiceException(UserServiceError.USER_NOT_FOUND);
        }
    }
}

