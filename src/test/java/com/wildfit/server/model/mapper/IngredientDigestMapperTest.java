package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IngredientDigestMapperTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void createIngredient() {
        IngredientDigest ingredientDigest = new IngredientDigest();

        assertThrows(WildfitServiceException.class, () -> IngredientDigestMapper.createIngredient(ingredientDigest));
        ingredientDigest.setFoodName("Apple");
        assertThrows(WildfitServiceException.class, () -> IngredientDigestMapper.createIngredient(ingredientDigest));
    }

    @Test
    void testCreateIngredient() {
        Ingredient ingredient = new Ingredient();
        IngredientDigest ingredientDigest = IngredientDigestMapper.createIngredient(ingredient);

        assertNotNull(ingredientDigest);
    }

    @Test
    void updateIngredient() {
        IngredientDigest ingredientDigest = new IngredientDigest();
        Ingredient ingredient = new Ingredient();

        Ingredient updated = IngredientDigestMapper.updateIngredient(ingredient, ingredientDigest);
        assertNotNull(updated);
    }
}