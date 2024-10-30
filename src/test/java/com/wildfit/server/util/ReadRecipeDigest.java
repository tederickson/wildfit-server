package com.wildfit.server.util;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Recipe;
import com.wildfit.server.model.mapper.RecipeMapper;

import static org.junit.jupiter.api.Assertions.fail;

public class ReadRecipeDigest {
    public static final String RECIPE_EMAIL = "bob@bob.net";

    public static RecipeDigest getRecipeDigest(String fileName) {
        try (var in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            final var mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            return mapper.readValue(in, RecipeDigest.class);
        } catch (Exception e) {
            fail(fileName + " : " + e.getMessage());
        }
        return RecipeDigest.builder().build(); // never reached
    }

    public static Recipe getRecipe(String fileName) {
        final var digest = getRecipeDigest(fileName);

        try {
            return RecipeMapper.create(digest, RECIPE_EMAIL);
        } catch (WildfitServiceException e) {
            fail(fileName + " : " + e.getMessage());
        }
        return null; // never reached
    }
}
