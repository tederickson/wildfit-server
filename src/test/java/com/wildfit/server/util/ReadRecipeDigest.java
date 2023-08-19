package com.wildfit.server.util;

import static org.junit.jupiter.api.Assertions.fail;

import com.wildfit.server.domain.RecipeDigest;

public class ReadRecipeDigest {
    public static RecipeDigest getRecipeDigest(String fileName) {
        try (var in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            final var mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            return mapper.readValue(in, RecipeDigest.class);
        } catch (Exception e) {
            fail(fileName + " : " + e.getMessage());
        }
        return RecipeDigest.builder().build(); // never reached
    }
}
