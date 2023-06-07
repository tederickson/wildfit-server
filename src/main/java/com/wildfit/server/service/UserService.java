package com.wildfit.server.service;

import com.wildfit.server.domain.*;

public interface UserService {
    void createUser(UserDigest userDigest);

    void login(UserDigest userDigest);

    UserProfileDigest getUser(UserDigest userDigest)
}
