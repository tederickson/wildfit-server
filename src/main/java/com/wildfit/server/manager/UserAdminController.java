package com.wildfit.server.manager;

import com.wildfit.server.domain.CreateUserRequest;
import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.domain.UserDigest;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        log.info("createUser|" + request);
        final var userDigest = UserDigest.builder()
                .withEmail(request.getEmail())
                .withPassword(request.getPassword())
                .build();
        return userService.createUser(userDigest);
    }

    @ApiOperation(value = "Get User Profile")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Get user", response = UserProfileDigest.class), //
            @ApiResponse(code = 400, message = "User id not found")})
    @GetMapping("/users/{id}")
    public UserProfileDigest getUser(@PathVariable("id") Long id) throws UserServiceException {
        log.info("getUser|" + id);

        return userService.getUserProfile(id);
    }
}
