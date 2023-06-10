package com.wildfit.server.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wildfit.server.deserialization.CustomStringDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class UserDigest {
    private long id;
    @JsonDeserialize(using = CustomStringDeserializer.class)
    private String email;
    private UserStatusType status;
}
