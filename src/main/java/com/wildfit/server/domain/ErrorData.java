package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record ErrorData(String message, String errorCode, Integer nutritionixStatusCode) {
}
