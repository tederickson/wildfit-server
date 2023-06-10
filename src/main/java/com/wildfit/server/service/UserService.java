package com.wildfit.server.service;

import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.domain.UpdateUserProfileRequest;
import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceException;

public interface UserService {
    CreateUserResponse createUser(String email, String password) throws UserServiceException;

    void deleteUser(Long userId) throws UserServiceException;

    void login(UserDigest userDigest) throws UserServiceException;

    void changePassword(UserDigest userDigest) throws UserServiceException;

    UserProfileDigest getUserProfile(Long userId) throws UserServiceException;

    UserProfileDigest updateUserProfile(Long id, UpdateUserProfileRequest request) throws UserServiceException;
}
