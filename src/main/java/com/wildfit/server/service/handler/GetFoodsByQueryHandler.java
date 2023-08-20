package com.wildfit.server.service.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.SearchedFoodItem;
import com.wildfit.server.model.SearchedFoodItems;
import com.wildfit.server.model.mapper.SearchedFoodItemsMapper;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@SuperBuilder(setterPrefix = "with")
public class GetFoodsByQueryHandler extends AbstractNutritionixHandler<SearchFoodResponse> {
    private final String description;

    @Override
    protected SearchFoodResponse executeInHandler() {
        final var restTemplate = new RestTemplate();
        final var entity = new HttpEntity<>(getHeaders());

        final var queryParameters = String.join("&",
                "query=" + description,
                // brand type to filter branded results by. 1=Restaurant, 2=Grocery
                "branded_type=2",
                // whether to include detailed nutrient fields like full_nutrients and serving_weight_grams
                "detailed=true");

        url = NUTRITIONIX_URL + "v2/search/instant?" + queryParameters;


        final var searchedFoodItems = restTemplate.exchange(url, HttpMethod.GET, entity, SearchedFoodItems.class)
                                                  .getBody();
        Objects.requireNonNull(searchedFoodItems, "searchedFoodItems");

        // How do I filter out duplicate common foods from the /search/instant endpoint?
        // To provide the best experience to the end user, it is recommended to filter common food results
        // on “tag_id”. Common foods with the same tag_id have identical nutrition
        // (ex. “Blackberry” and “Blackberries”).
        // For best results, filter the results to show only one food per tag_id. Generally, the first one will
        // be the most relevant based on the user search query.
        SearchedFoodItem[] common = filterByTagId(searchedFoodItems.getCommon());

        common = filterByDescription(common);

        SearchedFoodItem[] branded = searchedFoodItems.getBranded();

        final var updatedResponse = new SearchedFoodItems();
        updatedResponse.setCommon(common);
        updatedResponse.setBranded(branded);

        return SearchedFoodItemsMapper.map(updatedResponse);
    }

    @Override
    protected void validate() throws WildfitServiceException {
        super.validate();

        if (StringUtils.isAllBlank(description)) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }

    private SearchedFoodItem[] filterByDescription(SearchedFoodItem[] common) {
        // 'coconut oil' returns "coconut oil", "coconut milk", "mct oil", ...
        final var matchedDescriptions = new ArrayList<SearchedFoodItem>();

        final var descriptions = description.split(" ");
        for (var foodItem : common) {
            if (containsAll(foodItem.getFood_name(), descriptions)) {
                matchedDescriptions.add(foodItem);
            }
        }
        return matchedDescriptions.toArray(new SearchedFoodItem[0]);
    }

    private boolean containsAll(String foodName, String[] descriptions) {
        for (var text : descriptions) {
            if (!foodName.contains(text)) {
                return false;
            }
        }
        return true;
    }


    private SearchedFoodItem[] filterByTagId(SearchedFoodItem[] common) {
        final var uniqueTags = new ArrayList<SearchedFoodItem>();
        final var tagIds = new HashSet<String>();

        for (var foodItem : common) {
            if (tagIds.add(foodItem.getTag_id())) {
                uniqueTags.add(foodItem);
            }
        }

        return uniqueTags.toArray(new SearchedFoodItem[0]);
    }
}
