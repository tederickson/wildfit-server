package com.wildfit.server.service;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.domain.UserProfileDigest;
import com.wildfit.server.exception.UserServiceException;

public interface UserService {
    UserDigest createUser(UserDigest userDigest) throws UserServiceException;

    void deleteUser(UserDigest userDigest);

    void login(UserDigest userDigest);

    void changePassword(UserDigest userDigest);

    UserProfileDigest getUserProfile(UserDigest userDigest);

    UserProfileDigest updateUserProfile(UserProfileDigest userProfileDigest);


}
