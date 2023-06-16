package com.wildfit.server.service;

import java.util.List;
import java.util.Optional;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.FoodItems;
import com.wildfit.server.model.NutritionixHeaderInfo;
import com.wildfit.server.model.SearchedFoodItems;
import com.wildfit.server.model.mapper.FoodItemDigestMapper;
import com.wildfit.server.model.mapper.SearchedFoodItemsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * The service calls Handlers to implement the functionality.
 * This provides loose coupling, allows several people to work on the service without merge collisions
 * and provides a lightweight service.
 */
@Slf4j
@Service
public class NutritionixServiceImpl implements NutritionixService {
    private final String NUTRITIONIX_URL = "https://trackapi.nutritionix.com/";
    @Autowired
    private NutritionixHeaderInfo nutritionixHeaderInfo;

    @Override
    public FoodItemDigest getFoodWithBarcode(String barcode) throws UserServiceException, NutritionixException {
        if (StringUtils.isAllBlank(barcode)) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        final var restTemplate = new RestTemplate();
        final var headers = new HttpHeaders();

        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        addNutritionixHeaders(headers);

        final var entity = new HttpEntity<String>(headers);
        final var url = NUTRITIONIX_URL + "v2/search/item?upc=" + barcode;

        try {
            final var foodItems = restTemplate.exchange(url, HttpMethod.GET, entity, FoodItems.class).getBody();
            final var numFoods = Optional.ofNullable(foodItems)
                    .map(FoodItems::getFoods)
                    .map(x -> x.length)
                    .orElse(0);

            if (numFoods == 0) {
                return FoodItemDigest.builder().build();
            }
            return FoodItemDigestMapper.map(foodItems.getFoods()[0]);

        } catch (HttpStatusCodeException e) {
            throw new NutritionixException(e.getStatusCode(), e);
        }
    }

    @Override
    public FoodItemDigest getFoodWithId(String nixItemId) throws UserServiceException, NutritionixException {
        if (StringUtils.isAllBlank(nixItemId)) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        final var restTemplate = new RestTemplate();
        final var headers = new HttpHeaders();

        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        addNutritionixHeaders(headers);

        final var entity = new HttpEntity<String>(headers);
        final var url = NUTRITIONIX_URL + "v2/search/item?nix_item_id=" + nixItemId;

        try {
            final var foodItems = restTemplate.exchange(url, HttpMethod.GET, entity, FoodItems.class).getBody();
            final var numFoods = Optional.ofNullable(foodItems)
                    .map(FoodItems::getFoods)
                    .map(x -> x.length)
                    .orElse(0);

            if (numFoods == 0) {
                return FoodItemDigest.builder().build();
            }
            return FoodItemDigestMapper.map(foodItems.getFoods()[0]);

        } catch (HttpStatusCodeException e) {
            throw new NutritionixException(e.getStatusCode(), e);
        }
    }

    @Override
    public SearchFoodResponse getFoodsByQuery(String description) throws UserServiceException, NutritionixException {
        if (StringUtils.isAllBlank(description)) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        final var restTemplate = new RestTemplate();
        final var headers = new HttpHeaders();

        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        addNutritionixHeaders(headers);

        final var queryParameters = String.join("&",
                "query=" + description,
                "branded_type=2");

        final var url = NUTRITIONIX_URL + "v2/search/instant?" + queryParameters;
        log.info(url);

        try {
            final var response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(headers),
                    SearchedFoodItems.class).getBody();
            return SearchedFoodItemsMapper.map(response);
        } catch (HttpStatusCodeException e) {
            throw new NutritionixException(e.getStatusCode(), e);
        }
    }

    private void addNutritionixHeaders(HttpHeaders headers) {
        headers.add("x-app-id", nutritionixHeaderInfo.getAppId());
        headers.add("x-app-key", nutritionixHeaderInfo.getAppKey());
        headers.add("x-remote-user-id", nutritionixHeaderInfo.getRemoteUserId());
    }
}
