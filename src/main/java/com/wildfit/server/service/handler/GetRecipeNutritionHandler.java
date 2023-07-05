package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.ParseRecipeRequest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.FoodItems;
import com.wildfit.server.model.mapper.FoodItemDigestMapper;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@SuperBuilder(setterPrefix = "with")
public class GetRecipeNutritionHandler extends AbstractNutritionixHandler<FoodItemDigest> {
    private final ParseRecipeRequest parseRecipeRequest;

    @Override
    protected FoodItemDigest executeInHandler() {
        final var restTemplate = new RestTemplate();
        final var entity = new HttpEntity<>(parseRecipeRequest, getHeaders());

        url = NUTRITIONIX_URL + "v2/natural/nutrients";

        final var foodItems = restTemplate.exchange(url, HttpMethod.POST, entity, FoodItems.class).getBody();
        Objects.requireNonNull(foodItems, "foodItems");

        return FoodItemDigestMapper.map(foodItems.getFoods()[0]);
    }

    @Override
    protected void validate() throws UserServiceException {
        super.validate();

        Objects.requireNonNull(parseRecipeRequest, "parseRecipeRequest");

        if (StringUtils.isAllBlank(parseRecipeRequest.getQuery())) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (StringUtils.isAllBlank(parseRecipeRequest.getAggregate())) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        Objects.requireNonNull(parseRecipeRequest.getNum_servings(), "parseRecipeRequest.getNum_servings()");
    }
}
