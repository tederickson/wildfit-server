package com.wildfit.server.domain;

import lombok.Builder;
import lombok.*;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class UserDigest {
    private String userName;
    private String password;
    private String email;
}
