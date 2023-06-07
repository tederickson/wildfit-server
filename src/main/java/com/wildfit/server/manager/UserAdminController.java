package com.wildfit.server.manager;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class UserAdminController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Create User")
    @ApiResponses(value = { //
            @ApiResponse(code = 201, message = "Successfully created user"), //
            @ApiResponse(code = 400, message = "Invalid user name and/or password or user already exists")})
    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDigest createUser(@RequestBody UserDigest userDigest) throws UserServiceException {
        log.info("/users/" + userDigest);

        return userService.createUser(userDigest);
    }
}
