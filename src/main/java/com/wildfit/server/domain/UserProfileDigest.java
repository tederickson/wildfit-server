package com.wildfit.server.domain;

import lombok.Data;

@Data
public final class UserProfileDigest {
    private UserDigest user;

    private int age;
    private char gender;
    private float height;
    private float weight;
}
