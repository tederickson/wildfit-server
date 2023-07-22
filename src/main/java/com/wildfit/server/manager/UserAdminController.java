package com.wildfit.server.manager;

import com.wildfit.server.domain.ChangePasswordRequest;
import com.wildfit.server.domain.CreateUserRequest;
import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.domain.UpdateUserProfileRequest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exceptions are handled by the ManagerAdvice. The exception is logged
 * and converted to the appropriate HTTP Status code.
 */
@RestController
@Slf4j
@Tag(name = "User Administration API")
@RequestMapping("v1/users")
public class UserAdminController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Create User")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "201", description = "Successfully created user"), //
            @ApiResponse(responseCode = "400", description = "Invalid user name and/or password or user already exists"),
            @ApiResponse(responseCode = "412", description = "Email not configured")})
    @PostMapping(produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws UserServiceException {
        final var logMessage = String.join("|", "createUser", request.toString());
        log.info(logMessage);

        return userService.createUser(request.getEmail(), request.getPassword(), request.getName());
    }

    @Operation(summary = "Get User Profile")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Get user"), //
            @ApiResponse(responseCode = "404", description = "User id not found")})
    @GetMapping(value = "/{id}", produces = "application/json")
    public UserProfileDigest getUser(@PathVariable("id") String id) throws UserServiceException {
        final var logMessage = String.join("|", "getUser", id);
        log.info(logMessage);

        return userService.getUserProfile(id);
    }

    @Operation(summary = "Update User Profile")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Update user"), //
            @ApiResponse(responseCode = "404", description = "User id not found")})
    @PutMapping(value = "/{id}", produces = "application/json")
    public UserProfileDigest updateUserProfile(@PathVariable("id") String id,
                                               @RequestBody UpdateUserProfileRequest request)
            throws UserServiceException {
        final var logMessage = String.join("|", "updateUserProfile", id, request.toString());
        log.info(logMessage);

        return userService.updateUserProfile(id, request);
    }

    @Operation(summary = "Delete User")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "User deleted"), //
            @ApiResponse(responseCode = "404", description = "User id not found")})
    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id") String id) throws UserServiceException {
        final var logMessage = String.join("|", "deleteUser", id);
        log.info(logMessage);

        userService.deleteUser(id);
    }

    @Operation(summary = "Change Password")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Successfully changed password"), //
            @ApiResponse(responseCode = "400", description = "Invalid user id and/or password")})
    @PostMapping(value = "/{id}/change-password")
    public void changePassword(@PathVariable("id") String id, @RequestBody ChangePasswordRequest request)
            throws UserServiceException {
        final var logMessage = String.join("|", "changePassword", id, request.toString());
        log.info(logMessage);

        userService.changePassword(id, request.getPassword());
    }

}
