package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.FoodItemDigest;
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
public class GetFoodWithIdHandler extends AbstractNutritionixHandler<FoodItemDigest> {
    private final String nixItemId;

    @Override
    protected FoodItemDigest executeInHandler() {
        final var restTemplate = new RestTemplate();
        final var entity = new HttpEntity<>(getHeaders());
        url = NUTRITIONIX_URL + "v2/search/item?nix_item_id=" + nixItemId;

        final var foodItems = restTemplate.exchange(url, HttpMethod.GET, entity, FoodItems.class).getBody();
        Objects.requireNonNull(foodItems, "foodItems");

        return FoodItemDigestMapper.mapFoodItem(foodItems.getFoods()[0]);
    }

    @Override
    protected void validate() throws UserServiceException {
        super.validate();

        if (StringUtils.isAllBlank(nixItemId)) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
