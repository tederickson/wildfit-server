package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.model.FoodItems;
import com.wildfit.server.model.Season;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CreateRecipeHandlerTest extends CommonRecipeHandlerTest {

    private static void validateTunaSalad(com.wildfit.server.domain.RecipeDigest digest) {
        assertNotNull(digest);

        assertEquals("Tuna salad", digest.getName());
        assertEquals(com.wildfit.server.domain.SeasonType.SPRING, digest.getSeason());
        assertEquals(15, digest.getPrepTimeMin());
        assertEquals(0, digest.getCookTimeMin());
        assertEquals("serving", digest.getServingUnit());
        assertEquals(4, digest.getServingQty());
        assertEquals("Tuna is one of the staples in our household. \nWe eat it all the time, " +
                        "because it is simple and can be eaten for breakfast, lunch, dinner or snack.",
                digest.getIntroduction());

        assertEquals(2, digest.getInstructionGroups().size());

        for (var instructionGroup : digest.getInstructionGroups()) {
            switch (instructionGroup.getName()) {
                case "Salad" -> {
                    assertEquals(4, instructionGroup.getInstructions().size());
                    assertEquals(5, instructionGroup.getIngredients().size());
                }
                case "Dressing" -> {
                    assertEquals(0, instructionGroup.getInstructions().size());
                    assertEquals(4, instructionGroup.getIngredients().size());
                }
                default -> fail(instructionGroup.getName());
            }
        }
    }

    @Test
    void execute() throws Exception {
        final var season = SeasonType.SPRING;
        final var name = "CreateRecipeHandlerTest";

        final var instructionGroup = InstructionGroupDigest.builder()
                                                           .withInstructionGroupNumber(1)
                                                           .withInstructions(List.of(step1, step2, step3, step4))
                                                           .build();
        final var recipe = RecipeDigest.builder()
                                       .withName(name)
                                       .withSeason(season)
                                       .withIntroduction(INTRODUCTION)
                                       .withPrepTimeMin(5)
                                       .withCookTimeMin(15)
                                       .withServingQty(4)
                                       .withServingUnit("serving")
                                       .withInstructionGroups(List.of(instructionGroup))
                                       .build();
        createRecipe(recipe);

        assertNotNull(testRecipe);

        final var dbRecipeId = testRecipe.getId();
        final var dbRecipeGroupId = Iterables.getOnlyElement(testRecipe.getInstructionGroups()).getId();
        final var dbRecipe = recipe1Repository.findById(dbRecipeId).orElseThrow();
        assertEquals(name, dbRecipe.getName());
        assertEquals(Season.SPRING.name(), dbRecipe.getSeasonName());


    }

    @Test
    public void processTuna_salad() throws Exception {
        final var digest = getRecipeDigest("Tuna_salad.json");
        validateTunaSalad(digest);

        createRecipe(digest);

        validateTunaSalad(testRecipe);
    }

    private RecipeDigest getRecipeDigest(String fileName) throws IOException {
        try (var in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            final var mapper = new ObjectMapper();

            return mapper.readValue(in, RecipeDigest.class);
        }
    }
}