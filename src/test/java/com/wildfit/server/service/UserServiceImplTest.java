package com.wildfit.server.service;

import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.repository.VerificationTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final String email = null;
    private static final String password = null;
    private static final String name = null;
    private static final String userId = null;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserProfileRepository userProfileRepository;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;
    @Mock
    private Environment environment;
    @Mock
    private JavaMailSender javaMailSender;

    @Test
    void createUser() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.createUser(email, password, name));
        assertEquals("Missing email.", exception.getMessage());
    }

    @Test
    void deleteUser() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.deleteUser(userId));
        assertEquals("User not found.", exception.getMessage());
    }

    @Test
    void changePassword() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.changePassword(userId, password));
        assertEquals("User not found.", exception.getMessage());
    }

    @Test
    void login() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.login(email, password));
        assertEquals("Missing email.", exception.getMessage());
    }

    @Test
    void getUserProfile() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.getUserProfile(userId));
        assertEquals("User not found.", exception.getMessage());
    }

    @Test
    void updateUserProfile() {
        final var exception = assertThrows(NullPointerException.class,
                                           () -> userService.updateUserProfile(userId, null));
        assertEquals("userId", exception.getMessage());
    }

    @Test
    void confirmUser() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.confirmUser(null));
        assertEquals("Invalid confirmation code.", exception.getMessage());
    }
}