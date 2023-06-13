package com.wildfit.server.manager;

import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            @ApiResponse(code = 200, message = "Confirm user email account"), //
            @ApiResponse(code = 400, message = "Confirmation code not found")})
    @PutMapping("/auth/register")
    public void register(@RequestParam(value = "confirmCode", required = true) String confirmCode) throws UserServiceException {
        final var logMessage = String.join("|", "register", confirmCode);
        log.info(logMessage);

        userService.confirmUser(confirmCode);
    }
}
