package com.wildfit.server.model;

import com.wildfit.server.domain.UserStatusType;
import org.junit.jupiter.api.Test;

class UserStatusTest {

    @Test
    void tiedToUserStatusType() {
        for (var enm : UserStatus.values()) {
            UserStatusType.valueOf(enm.name());
        }
        for (var enm : UserStatusType.values()) {
            UserStatus.valueOf(enm.name());
        }
    }
}