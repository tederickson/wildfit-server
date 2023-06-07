package com.wildfit.server.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public final class UserDigest {
    private String userName;
    private String password;
    private String email;
}
