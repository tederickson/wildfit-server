package com.wildfit.server.service;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.service.handler.CreateUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The service calls Handlers to implement the functionality.
 * This provides loose coupling, allows several people to work on the service without merge collisions
 * and provides a lightweight service.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDigest createUser(UserDigest userDigest) throws UserServiceException {
        return CreateUserHandler.builder()
                .withUserRepository(userRepository)
                .withUserDigest(userDigest)
                .build().execute();
    }

    @Override
    public void deleteUser(UserDigest userDigest) {

    }

    @Override
    public void login(UserDigest userDigest) {

    }

    @Override
    public void changePassword(UserDigest userDigest) {

    }

    @Override
    public UserProfileDigest getUserProfile(UserDigest userDigest) {
        return null;
    }

    @Override
    public UserProfileDigest updateUserProfile(UserProfileDigest userProfileDigest) {
        return null;
    }
}
