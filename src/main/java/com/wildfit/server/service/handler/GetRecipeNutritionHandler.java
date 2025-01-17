package com.wildfit.server.service.handler;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.ParseRecipeRequest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeGroupDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.FoodItems;
import com.wildfit.server.model.mapper.FoodItemDigestMapper;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Slf4j
@SuperBuilder(setterPrefix = "with")
public class GetRecipeNutritionHandler extends AbstractNutritionixHandler<FoodItemDigest> {
    private final RecipeDigest recipeDigest;

    @Override
    protected FoodItemDigest executeInHandler() {
        final var parseRecipeRequest = new ParseRecipeRequest();
        parseRecipeRequest.setAggregate(recipeDigest.getName());
        parseRecipeRequest.setNum_servings(recipeDigest.getServingQty());

        recipeDigest.getRecipeGroups().stream()
                .map(RecipeGroupDigest::ingredients)
                .flatMap(List::stream)
                .forEach((ingredient) -> parseRecipeRequest.addIngredient(
                        ingredient.ingredientServingQty(),
                        ingredient.ingredientServingUnit(),
                        ingredient.foodName()));

        log.info("{}, {} servings\n{}", recipeDigest.getName(), parseRecipeRequest.getNum_servings(),
                 parseRecipeRequest.getQuery());

        final var restTemplate = new RestTemplate();
        final var entity = new HttpEntity<>(parseRecipeRequest, getHeaders());

        url = NUTRITIONIX_URL + "v2/natural/nutrients";

        final var foodItems = restTemplate.exchange(url, HttpMethod.POST, entity, FoodItems.class).getBody();
        Objects.requireNonNull(foodItems, "foodItems");

        return FoodItemDigestMapper.mapFoodItem(foodItems.getFoods()[0]);
    }

    @Override
    protected void validate() throws WildfitServiceException {
        super.validate();
        if (recipeDigest == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
