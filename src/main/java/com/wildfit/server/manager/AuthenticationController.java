package com.wildfit.server.manager;

import com.wildfit.server.domain.LoginRequest;
import com.wildfit.server.domain.RegisterUserResponse;
import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exceptions are handled by the ManagerAdvice. The exception is logged
 * and converted to the appropriate HTTP Status code.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication API")
@RequestMapping("v1/auth")
public class AuthenticationController {
    private final UserService userService;

    @Operation(summary = "Confirm User Email")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Confirm user email account"), //
            @ApiResponse(responseCode = "404", description = "Confirmation code not found")})
    @GetMapping("/register/{confirmCode}")
    public RegisterUserResponse register(@PathVariable("confirmCode") String confirmCode)
            throws WildfitServiceException {
        return userService.confirmUser(confirmCode);
    }

    @Operation(summary = "Log in User")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Successfully logged in user"), //
            @ApiResponse(responseCode = "400", description = "Invalid user name and/or password")})
    @PostMapping("/login")
    public UserDigest login(@RequestBody LoginRequest request) throws WildfitServiceException {
        return userService.login(request.getEmail(), request.getPassword());
    }

}
