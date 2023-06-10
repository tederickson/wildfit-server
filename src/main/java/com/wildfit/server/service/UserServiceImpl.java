package com.wildfit.server.service;

import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.domain.UpdateUserProfileRequest;
import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.service.handler.CreateUserHandler;
import com.wildfit.server.service.handler.DeleteUserHandler;
import com.wildfit.server.service.handler.GetUserProfileHandler;
import com.wildfit.server.service.handler.UpdateUserProfileHandler;
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
    public CreateUserResponse createUser(String email, String password) throws UserServiceException {
        return CreateUserHandler.builder()
                .withUserRepository(userRepository)
                .withUserProfileRepository(userProfileRepository)
                .withEmail(email)
                .withPassword(password)
                .build().execute();
    }

    @Override
    public void deleteUser(Long userId) throws UserServiceException {
        DeleteUserHandler.builder()
                .withUserRepository(userRepository)
                .withUserId(userId)
                .build().execute();
    }

    @Override
    public void login(UserDigest userDigest) throws UserServiceException {

    }

    @Override
    public void changePassword(UserDigest userDigest) throws UserServiceException {

    }

    @Override
    public UserProfileDigest getUserProfile(Long userId) throws UserServiceException {
        return GetUserProfileHandler.builder()
                .withUserRepository(userRepository)
                .withUserProfileRepository(userProfileRepository)
                .withUserId(userId)
                .build().execute();
    }

    @Override
    public UserProfileDigest updateUserProfile(Long id, UpdateUserProfileRequest request) throws UserServiceException {
        return UpdateUserProfileHandler.builder()
                .withUserRepository(userRepository)
                .withUserProfileRepository(userProfileRepository)
                .withUserId(id)
                .withUserProfileRequest(request)
                .build().execute();
    }
}
