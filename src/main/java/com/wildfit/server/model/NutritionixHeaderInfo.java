package com.wildfit.server.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
public class NutritionixHeaderInfo {
    @Value("${x-app-id}")
    private String appId;

    @Value("${x-app-key}")
    private String appKey;

    @Value("${x-remote-user-id}")
    private String remoteUserId;
}
