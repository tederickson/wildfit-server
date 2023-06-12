package com.wildfit.server.manager;

import com.wildfit.server.domain.ConfirmUserRequest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
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
            @ApiResponse(code = 200, message = "Confirm user", response = UserProfileDigest.class), //
            @ApiResponse(code = 400, message = "User email not found")})
    @PutMapping("/auth/confirm")
    public void confirmUser(@RequestBody ConfirmUserRequest request) throws UserServiceException {
        final var logMessage = String.join("|", "confirmUser", request.toString());
        log.info(logMessage);

        userService.confirmUser(request.getEmail(), request.getConfirmationCode());
    }
}
