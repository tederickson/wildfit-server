package com.wildfit.server.service;

import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceException;

public interface UserService {
    CreateUserResponse createUser(UserDigest userDigest) throws UserServiceException;

    void deleteUser(UserDigest userDigest);

    void login(UserDigest userDigest);

    void changePassword(UserDigest userDigest);

    UserProfileDigest getUserProfile(Long userId) throws UserServiceException;

    UserProfileDigest updateUserProfile(UserProfileDigest userProfileDigest);


}
