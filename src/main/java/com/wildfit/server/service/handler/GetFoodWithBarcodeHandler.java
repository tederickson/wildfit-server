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
public class GetFoodWithBarcodeHandler extends AbstractNutritionixHandler<FoodItemDigest> {
    private final String barcode;

    @Override
    public FoodItemDigest executeInHandler() {
        final var restTemplate = new RestTemplate();
        final var entity = new HttpEntity<String>(getHeaders());

        url = NUTRITIONIX_URL + "v2/search/item?upc=" + barcode;

        final var foodItems = restTemplate.exchange(url, HttpMethod.GET, entity, FoodItems.class).getBody();
        Objects.requireNonNull(foodItems, "foodItems");

        return FoodItemDigestMapper.map(foodItems.getFoods()[0]);
    }

    @Override
    protected void validate() throws UserServiceException {
        super.validate();

        if (StringUtils.isAllBlank(barcode)) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
