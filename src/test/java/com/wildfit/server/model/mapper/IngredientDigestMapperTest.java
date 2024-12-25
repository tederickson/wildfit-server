package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Ingredient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IngredientDigestMapperTest {

    @Test
    void createIngredient() {
        var ingredientDigest = IngredientDigest.builder();

        assertThrows(WildfitServiceException.class,
                     () -> IngredientDigestMapper.createIngredient(ingredientDigest.build()));
        ingredientDigest.withFoodName("Apple");
        assertThrows(WildfitServiceException.class,
                     () -> IngredientDigestMapper.createIngredient(ingredientDigest.build()));
    }

    @Test
    void testCreateIngredient() {
        Ingredient ingredient = new Ingredient();
        IngredientDigest ingredientDigest = IngredientDigestMapper.createIngredient(ingredient);

        assertNotNull(ingredientDigest);
    }

    @Test
    void updateIngredient() {
        var ingredientDigest = IngredientDigest.builder();
        Ingredient ingredient = new Ingredient();

        Ingredient updated = IngredientDigestMapper.updateIngredient(ingredient, ingredientDigest.build());
        assertNotNull(updated);
    }
}