package com.wildfit.server.service.handler;

import java.util.List;
import java.util.Objects;

import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.NutritionixHeaderInfo;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@SuperBuilder(setterPrefix = "with")
public abstract class AbstractNutritionixHandler<T> {
    protected final String NUTRITIONIX_URL = "https://trackapi.nutritionix.com/";

    protected final NutritionixHeaderInfo nutritionixHeaderInfo;
    protected String url;

    protected abstract T executeInHandler();

    public T execute() throws UserServiceException, NutritionixException {
        validate();

        try {
            return executeInHandler();
        } catch (HttpStatusCodeException e) {
            log.error(url);
            throw new NutritionixException(e.getStatusCode(), e);
        }
    }

    protected HttpHeaders getHeaders() {
        final var headers = new HttpHeaders();

        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("x-app-id", nutritionixHeaderInfo.getAppId());
        headers.add("x-app-key", nutritionixHeaderInfo.getAppKey());
        //  headers.add("x-remote-user-id", nutritionixHeaderInfo.getRemoteUserId())

        return headers;
    }

    protected void validate() throws UserServiceException {
        Objects.requireNonNull(nutritionixHeaderInfo, "nutritionixHeaderInfo");
    }
}
