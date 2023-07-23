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
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * The service calls Handlers to implement the functionality.
 * This provides loose coupling, a lightweight service, separation of concerns, and allows several people to work on
 * the same service without merge collisions.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final Environment environment;
    private final JavaMailSender javaMailSender;

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
    public void deleteUser(String userId) throws UserServiceException {
        DeleteUserHandler.builder()
                         .withUserRepository(userRepository)
                         .withUserId(userId)
                         .build().execute();
    }

    @Override
    public void changePassword(String userId, String password) throws UserServiceException {
        ChangePasswordHandler.builder()
                             .withUserRepository(userRepository)
                             .withUserId(userId)
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
    public UserProfileDigest getUserProfile(String userId) throws UserServiceException {
        return GetUserProfileHandler.builder()
                                    .withUserRepository(userRepository)
                                    .withUserProfileRepository(userProfileRepository)
                                    .withUserId(userId)
                                    .build().execute();
    }

    @Override
    public UserProfileDigest updateUserProfile(String userId, UpdateUserProfileRequest request)
            throws UserServiceException {
        return UpdateUserProfileHandler.builder()
                                       .withUserRepository(userRepository)
                                       .withUserProfileRepository(userProfileRepository)
                                       .withUserId(userId)
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
