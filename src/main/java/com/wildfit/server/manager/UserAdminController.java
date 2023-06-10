package com.wildfit.server.manager;

import com.wildfit.server.domain.ChangePasswordRequest;
import com.wildfit.server.domain.ConfirmUserRequest;
import com.wildfit.server.domain.CreateUserRequest;
import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.domain.UpdateUserProfileRequest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exceptions are handled by the ManagerAdvice. The exception is logged
 * and converted to the appropriate HTTP Status code.
 */
@RestController
@Slf4j
@Api(description = "User Administration API")
public class UserAdminController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Create User")
    @ApiResponses(value = { //
            @ApiResponse(code = 201, message = "Successfully created user", response = CreateUserResponse.class), //
            @ApiResponse(code = 400, message = "Invalid user name and/or password or user already exists")})
    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws UserServiceException {
        final var logMessage = String.join("|", "createUser", request.toString());
        log.info(logMessage);

        return userService.createUser(request.getEmail(), request.getPassword());
    }

    @ApiOperation(value = "Get User Profile")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Get user", response = UserProfileDigest.class), //
            @ApiResponse(code = 400, message = "User id not found")})
    @GetMapping("/users/{id}")
    public UserProfileDigest getUser(@PathVariable("id") Long id) throws UserServiceException {
        final var logMessage = String.join("|", "getUser", id.toString());
        log.info(logMessage);

        return userService.getUserProfile(id);
    }

    @ApiOperation(value = "Update User Profile")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Update user", response = UserProfileDigest.class), //
            @ApiResponse(code = 400, message = "User id not found")})
    @PutMapping("/users/{id}")
    public UserProfileDigest updateUserProfile(@PathVariable("id") Long id,
                                               @RequestBody UpdateUserProfileRequest request) throws UserServiceException {
        final var logMessage = String.join("|", "updateUserProfile", id.toString(), request.toString());
        log.info(logMessage);

        return userService.updateUserProfile(id, request);
    }

    @ApiOperation(value = "Delete User")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "User deleted"), //
            @ApiResponse(code = 400, message = "User id not found")})
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) throws UserServiceException {
        final var logMessage = String.join("|", "deleteUser", id.toString());
        log.info(logMessage);

        userService.deleteUser(id);
    }

    @ApiOperation(value = "Change Password")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Successfully changed password"), //
            @ApiResponse(code = 400, message = "Invalid user id and/or password")})
    @PostMapping("/users/{id}/change-password")
    public void changePassword(@PathVariable("id") Long id, @RequestBody ChangePasswordRequest request) throws UserServiceException {
        final var logMessage = String.join("|", "changePassword", id.toString(), request.toString());
        log.info(logMessage);

        userService.changePassword(id, request.getPassword());
    }

    @ApiOperation(value = "Confirm User Email")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Confirm user", response = UserProfileDigest.class), //
            @ApiResponse(code = 400, message = "User email not found")})
    @PutMapping("/users/confirm")
    public void confirmUser(@RequestBody ConfirmUserRequest request) throws UserServiceException {
        final var logMessage = String.join("|", "confirmUser", request.toString());
        log.info(logMessage);

        userService.confirmUser(request.getEmail(), request.getConfirmationCode());
    }
}
