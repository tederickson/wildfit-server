package com.wildfit.server.service.handler;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.FoodItem;
import com.wildfit.server.model.mapper.FoodItemDigestMapper;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

@SuperBuilder(setterPrefix = "with")
public class GetFoodWithBarcodeHandler extends AbstractNutritionixHandler<FoodItemDigest> {
    private final String barcode;

    @Override
    public FoodItemDigest executeInHandler() {
        //        final var restTemplate = new RestTemplate();
        //        final var entity = new HttpEntity<>(getHeaders());
        //
        //        url = NUTRITIONIX_URL + "v2/search/item?upc=" + barcode;
        //
        //        final var foodItems = restTemplate.exchange(url, HttpMethod.GET, entity, FoodItems.class).getBody();
        //        Objects.requireNonNull(foodItems, "foodItems");

        //       return FoodItemDigestMapper.mapFoodItem(foodItems.getFoods()[0]);
        return FoodItemDigestMapper.mapFoodItem(new FoodItem());
    }

    @Override
    protected void validate() throws WildfitServiceException {
        super.validate();

        if (StringUtils.isAllBlank(barcode)) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
