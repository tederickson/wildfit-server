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
public final class LoginRequest {
    @JsonDeserialize(using = CustomStringDeserializer.class)
    private String email;

    @JsonDeserialize(using = CustomStringDeserializer.class)
    private String password;
}
