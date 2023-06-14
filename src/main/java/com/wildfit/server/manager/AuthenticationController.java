package com.wildfit.server.manager;

import com.wildfit.server.domain.LoginRequest;
import com.wildfit.server.domain.RegisterUserResponse;
import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exceptions are handled by the ManagerAdvice. The exception is logged
 * and converted to the appropriate HTTP Status code.
 */
@RestController
@Slf4j
@Api(description = "Authentication API")
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Confirm User Email")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Confirm user email account", response = RegisterUserResponse.class), //
            @ApiResponse(code = 400, message = "Confirmation code not found")})
    @GetMapping("/auth/register/{confirmCode}")
    public RegisterUserResponse register(@PathVariable(value = "confirmCode") String confirmCode) throws UserServiceException {
        final var logMessage = String.join("|", "register", confirmCode);
        log.info(logMessage);

        return userService.confirmUser(confirmCode);
    }

    @ApiOperation(value = "Log in User")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Successfully logged in user", response = UserDigest.class), //
            @ApiResponse(code = 400, message = "Invalid user name and/or password")})
    @PostMapping("/auth/login")
    public UserDigest login(@RequestBody LoginRequest request) throws UserServiceException {
        final var logMessage = String.join("|", "login", request.toString());
        log.info(logMessage);

        return userService.login(request.getEmail(), request.getPassword());
    }

}
