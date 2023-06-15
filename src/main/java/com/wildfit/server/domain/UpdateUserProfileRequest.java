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
public final class UpdateUserProfileRequest {
    @JsonDeserialize(using = CustomStringDeserializer.class)
    private String name;
    private int age;
    private GenderType gender;
    private int heightFeet;
    private int heightInches;
    private float weight;
}
