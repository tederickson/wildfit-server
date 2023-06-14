package com.wildfit.server.service;

import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.domain.RegisterUserResponse;
import com.wildfit.server.domain.UpdateUserProfileRequest;
import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.repository.VerificationTokenRepository;
import com.wildfit.server.service.handler.ChangePasswordHandler;
import com.wildfit.server.service.handler.ConfirmUserHandler;
import com.wildfit.server.service.handler.CreateUserHandler;
import com.wildfit.server.service.handler.DeleteUserHandler;
import com.wildfit.server.service.handler.GetUserProfileHandler;
import com.wildfit.server.service.handler.LoginHandler;
import com.wildfit.server.service.handler.UpdateUserProfileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
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
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    Environment environment;
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public CreateUserResponse createUser(String email, String password, String name) throws UserServiceException {
        return CreateUserHandler.builder()
                .withUserRepository(userRepository)
                .withUserProfileRepository(userProfileRepository)
                .withVerificationTokenRepository(verificationTokenRepository)
                .withEnvironment(environment)
                .withJavaMailSender(javaMailSender)
                .withEmail(email)
                .withPassword(password)
                .withName(name)
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
    public void changePassword(Long id, String password) throws UserServiceException {
        ChangePasswordHandler.builder()
                .withUserRepository(userRepository)
                .withUserId(id)
                .withPassword(password)
                .build().execute();
    }

    @Override
    public UserDigest login(String email, String password) throws UserServiceException {
        return LoginHandler.builder()
                .withUserRepository(userRepository)
                .withEmail(email)
                .withPassword(password)
                .build().execute();
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

    @Override
    public RegisterUserResponse confirmUser(String confirmationCode) throws UserServiceException {
        return ConfirmUserHandler.builder()
                .withUserRepository(userRepository)
                .withVerificationTokenRepository(verificationTokenRepository)
                .withConfirmationCode(confirmationCode)
                .build().execute();
    }
}
