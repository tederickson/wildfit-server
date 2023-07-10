package com.wildfit.server.manager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wildfit.server.domain.ChangePasswordRequest;
import com.wildfit.server.domain.CreateUserRequest;
import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.domain.UpdateUserProfileRequest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserAdminControllerTest {
    static final String userId = "1nv4L1d";

    @Mock
    private UserService userService;

    @InjectMocks
    UserAdminController userAdminController;

    @Test
    void createUser() throws UserServiceException {
        when(userService.createUser(any(), any(), any())).thenReturn(CreateUserResponse.builder().build());
        final var response = userAdminController.createUser(CreateUserRequest.builder().build());

        assertNotNull(response);
    }

    @Test
    void getUser() throws UserServiceException {
        when(userService.getUserProfile(userId)).thenReturn(UserProfileDigest.builder().build());
        final var response = userAdminController.getUser(userId);

        assertNotNull(response);
    }

    @Test
    void updateUserProfile() throws UserServiceException {
        UpdateUserProfileRequest request = UpdateUserProfileRequest.builder().build();
        when(userService.updateUserProfile(userId, request))
                .thenReturn(UserProfileDigest.builder().build());

        final var response = userAdminController.updateUserProfile(userId, UpdateUserProfileRequest.builder().build());

        assertNotNull(response);
    }

    @Test
    void deleteUser() throws UserServiceException {
        userAdminController.deleteUser(userId);
    }

    @Test
    void changePassword() throws UserServiceException {
        userAdminController.changePassword(userId, ChangePasswordRequest.builder().build());
    }
}