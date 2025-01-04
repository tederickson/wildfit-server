package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")

public record UpdateUserProfileRequest(String name,
                                       int age,
                                       GenderType gender,
                                       int heightFeet,
                                       int heightInches,
                                       float weight) {
}
