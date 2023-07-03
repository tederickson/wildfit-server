package com.wildfit.server.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.FoodItems;
import com.wildfit.server.model.NutritionixHeaderInfo;
import com.wildfit.server.model.SearchedFoodItem;
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
        final var entity = new HttpEntity<String>(getHeaders());
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
            log.error(url);
            throw new NutritionixException(e.getStatusCode(), e);
        }
    }

    @Override
    public FoodItemDigest getFoodWithId(String nixItemId) throws UserServiceException, NutritionixException {
        if (StringUtils.isAllBlank(nixItemId)) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        final var restTemplate = new RestTemplate();
        final var entity = new HttpEntity<String>(getHeaders());
        final var url = NUTRITIONIX_URL + "v2/search/item?nix_item_id=" + nixItemId;
        log.info(url);

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
            log.error(url);
            throw new NutritionixException(e.getStatusCode(), e);
        }
    }

    @Override
    public SearchFoodResponse getFoodsByQuery(String description, String servingUnit)
            throws UserServiceException, NutritionixException {
        if (StringUtils.isAllBlank(description)) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        final var restTemplate = new RestTemplate();
        final var entity = new HttpEntity<String>(getHeaders());

        final var queryParameters = String.join("&",
                "query=" + description,
                // brand type to filter branded results by. 1=Restaurant, 2=Grocery
                "branded_type=2",
                // whether to include detailed nutrient fields like full_nutrients and serving_weight_grams
                "detailed=true");

        final var url = NUTRITIONIX_URL + "v2/search/instant?" + queryParameters;
        log.info(url);

        try {
            final var response = restTemplate.exchange(url, HttpMethod.GET, entity, SearchedFoodItems.class).getBody();
            if (response == null) {
                throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
            }

            // How do I filter out duplicate common foods from the /search/instant endpoint?
            // To provide the best experience to the end user, it is recommended to filter common food results
            // on “tag_id”. Common foods with the same tag_id have identical nutrition
            // (ex. “Blackberry” and “Blackberries”).
            // For best results, filter the results to show only one food per tag_id. Generally, the first one will
            // be the most relevant based on the user search query.
            SearchedFoodItem[] common = filterByTagId(response.getCommon());
            SearchedFoodItem[] branded = response.getBranded();

            if (servingUnit == null) {
                final var updatedResponse = new SearchedFoodItems();
                updatedResponse.setCommon(common);
                updatedResponse.setBranded(branded);

                return SearchedFoodItemsMapper.map(updatedResponse);
            }

            final var searchedFoodItems = new SearchedFoodItems();

            searchedFoodItems.setCommon(filter(common, servingUnit));
            searchedFoodItems.setBranded(filter(branded, servingUnit));
            return SearchedFoodItemsMapper.map(searchedFoodItems);

        } catch (HttpStatusCodeException e) {
            log.error(url);
            throw new NutritionixException(e.getStatusCode(), e);
        }
    }

    private SearchedFoodItem[] filterByTagId(SearchedFoodItem[] common) {
        final var uniqueTags = new ArrayList<SearchedFoodItem>();
        final Set tagIds = new HashSet<String>();

        for (var foodItem : common) {
            if (tagIds.add(foodItem.getTag_id())) {
                uniqueTags.add(foodItem);
            }
        }

        return uniqueTags.toArray(new SearchedFoodItem[0]);
    }

    private SearchedFoodItem[] filter(SearchedFoodItem[] foodItems, String servingUnit) {
        return Arrays.stream(foodItems)
                .filter(x -> servingUnit.equals(x.getServing_unit()))
                .toArray(SearchedFoodItem[]::new);
    }

    private HttpHeaders getHeaders() {
        final var headers = new HttpHeaders();

        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));
        headers.add("x-app-id", nutritionixHeaderInfo.getAppId());
        headers.add("x-app-key", nutritionixHeaderInfo.getAppKey());
        headers.add("x-remote-user-id", nutritionixHeaderInfo.getRemoteUserId());

        return headers;
    }
}
