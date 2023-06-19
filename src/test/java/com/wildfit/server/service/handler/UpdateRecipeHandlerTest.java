package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpdateRecipeHandlerTest extends AbstractRecipeHandlerTest {
    @Test
    void execute() throws UserServiceException {
        final var instructions = String.join("\n",
                "Heat the oil in a heavy frying pan over medium-high heat, then cook the beef until it’s " +
                        "cooked through and starting to brown, breaking apart with a turner as it cooks.",
                "",
                "While beef cooks, mix together the fish sauce, chili sauce, and water in a small bowl. Zest the skin " +
                        "of the lime and squeeze the juice. (You may need two limes to get enough juice, but only use " +
                        "the zest from one lime.) Thinly slice the green onions and chop the cilantro. " +
                        "Most iceberg lettuce does not need washing as long as you remove the outer leaves. " +
                        "Cut out the core and cut the lettuce into quarters to make 'cups' to hold the beef mixture.",
                "When the beef is done, add the chili sauce mixture and let it sizzle until the water has evaporated, " +
                        "stirring a few times to get the flavor mixed through the meat. Turn off the heat and stir in " +
                        "the lime zest, lime juice, sliced green onions, and chopped cilantro.",
                "",
                "Serve meat mixture with iceberg lettuce leaves. Fill with beef and wrap around it. Eaten with your " +
                        "hands.",
                "",
                "Easy-peasy!"
        );
        final var recipe = RecipeDigest.builder()
                .withName("TEST TEST TEST Chili Beef Lettuce Wraps")
                .withSeason(SeasonType.SPRING)
                .withIntroduction("These lettuce wraps are so easy and full of flavor! " +
                        "They make a great side dish, or are perfect for a healthy spring approved recipe.")
                .withPrepTimeMin(5)
                .withCookTimeMin(15)
                .withServingQty(4)
                .withServingUnit("serving")
                .withInstructions(instructions)
                .build();
        final var testRecipe = CreateRecipeHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withUserId(userId)
                .withRequest(recipe)
                .build().execute();
        assertNotNull(testRecipe);

        testRecipe.setInstructions("blah blah blah");

        final var response = UpdateRecipeHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withUserId(userId)
                .withRequest(testRecipe)
                .build().execute();

        assertEquals("blah blah blah", response.getInstructions());

        recipeRepository.deleteById(testRecipe.getId());
    }
}