package com.wildfit.server.domain;

import lombok.Data;

@Data
public final class UserDigest {
    private String userName;
    private String password;
    private String email;
}
