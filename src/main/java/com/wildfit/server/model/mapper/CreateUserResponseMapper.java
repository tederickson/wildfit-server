package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.model.User;

public final class CreateUserResponseMapper {
    private CreateUserResponseMapper() {
    }

    public static CreateUserResponse map(User user) {
        return CreateUserResponse.builder()
                                 .withId(user.getId())
                                 .withEmail(user.getEmail())
                                 .withUuid(user.getUuid())
                                 .build();
    }
}
