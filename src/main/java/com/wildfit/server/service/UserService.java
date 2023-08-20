package com.wildfit.server.service;

import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.domain.RegisterUserResponse;
import com.wildfit.server.domain.UpdateUserProfileRequest;
import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.WildfitServiceException;

public interface UserService {
    CreateUserResponse createUser(String email, String password, String name) throws WildfitServiceException;

    void deleteUser(String userId) throws WildfitServiceException;

    void changePassword(String userId, String password) throws WildfitServiceException;

    UserProfileDigest getUserProfile(String userId) throws WildfitServiceException;

    UserProfileDigest updateUserProfile(String userId, UpdateUserProfileRequest request) throws WildfitServiceException;

    RegisterUserResponse confirmUser(String confirmationCode) throws WildfitServiceException;

    UserDigest login(String email, String password) throws WildfitServiceException;
}
