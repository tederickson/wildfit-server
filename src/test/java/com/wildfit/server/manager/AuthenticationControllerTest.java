package com.wildfit.server.manager;

import com.wildfit.server.domain.LoginRequest;
import com.wildfit.server.domain.RegisterUserResponse;
import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    AuthenticationController controller;
    @Mock
    private UserService userService;

    @Test
    void register() throws WildfitServiceException {
        when(userService.confirmUser(any())).thenReturn(RegisterUserResponse.builder().build());

        final var response = controller.register("deft");
        assertNotNull(response);
    }

    @Test
    void login() throws WildfitServiceException {
        when(userService.login(any(), any())).thenReturn(UserDigest.builder().build());

        final var response = controller.login(LoginRequest.builder().build());
        assertNotNull(response);
    }
}