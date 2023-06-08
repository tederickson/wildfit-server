package com.wildfit.server.service;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.service.handler.CreateUserHandler;
import com.wildfit.server.service.handler.GetUserProfileHandler;
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
    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public UserDigest createUser(UserDigest userDigest) throws UserServiceException {
        return CreateUserHandler.builder()
                .withUserRepository(userRepository)
                .withUserProfileRepository(userProfileRepository)
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
    public UserProfileDigest getUserProfile(UserDigest userDigest) throws UserServiceException {
        return GetUserProfileHandler.builder()
                .withUserRepository(userRepository)
                .withUserProfileRepository(userProfileRepository)
                .withUserDigest(userDigest)
                .build().execute();
    }

    @Override
    public UserProfileDigest updateUserProfile(UserProfileDigest userProfileDigest) {
        return null;
    }
}
