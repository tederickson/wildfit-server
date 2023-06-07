package com.wildfit.server.manager;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/users")
    public UserDigest createUser(@RequestBody UserDigest userDigest) throws UserServiceException {
        log.info("/users/" + userDigest);

        return userService.createUser(userDigest);
    }
}
