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
public final class UserProfileDigest {
    private UserDigest user;

    @JsonDeserialize(using = CustomStringDeserializer.class)
    private String name;
    private int age;
    private GenderType gender;
    private float height;
    private float weight;
}
