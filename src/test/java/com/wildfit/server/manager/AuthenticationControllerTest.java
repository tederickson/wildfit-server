package com.wildfit.server.manager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wildfit.server.domain.LoginRequest;
import com.wildfit.server.domain.RegisterUserResponse;
import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    AuthenticationController controller;

    @Test
    void register() throws UserServiceException {
        when(userService.confirmUser(any())).thenReturn(RegisterUserResponse.builder().build());

        final var response = controller.register("deft");
        assertNotNull(response);
    }

    @Test
    void login() throws UserServiceException {
        when(userService.login(any(), any())).thenReturn(UserDigest.builder().build());

        final var response = controller.login(LoginRequest.builder().build());
        assertNotNull(response);
    }
}