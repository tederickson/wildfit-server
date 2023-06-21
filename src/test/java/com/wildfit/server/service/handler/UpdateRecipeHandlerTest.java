package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpdateRecipeHandlerTest extends AbstractRecipeHandlerTest {

    static final String NAME = "UpdateRecipeHandlerTest";

    static final InstructionDigest step1 = InstructionDigest.builder().withStepNumber(1)
            .withInstruction("Heat the oil in a heavy frying pan over medium-high heat, then cook the beef " +
                    "until it’s " +
                    "cooked through and starting to brown, breaking apart with a turner as it cooks.").build();
    static final InstructionDigest step2 = InstructionDigest.builder().withStepNumber(2)
            .withInstruction("While beef cooks, mix together the fish sauce, chili sauce, and water in a " +
                    "small bowl. Zest the skin " +
                    "of the lime and squeeze the juice. (You may need two limes to get enough juice, " +
                    "but only use " +
                    "the zest from one lime.) Thinly slice the green onions and chop the cilantro. " +
                    "Most iceberg lettuce does not need washing as long as you remove the outer leaves. " +
                    "Cut out the core and cut the lettuce into quarters to make 'cups' to hold the beef " +
                    "mixture.").build();
    final InstructionDigest step3 = InstructionDigest.builder().withStepNumber(3)
            .withInstruction("When the beef is done, add the chili sauce mixture and let it sizzle until " +
                    "the water has evaporated, " +
                    "stirring a few times to get the flavor mixed through the meat. Turn off the heat and " +
                    "stir in " +
                    "the lime zest, lime juice, sliced green onions, and chopped cilantro.").build();
    final InstructionDigest step4 = InstructionDigest.builder().withStepNumber(4)
            .withInstruction("Serve meat mixture with iceberg lettuce leaves. Fill with beef and wrap " +
                    "around it. Eaten with your hands.").build();

    static final String INTRODUCTION = "These lettuce wraps are so easy and full of flavor! " +
            "They make a great side dish, or are perfect for a healthy spring approved recipe.";

    @Test
    void execute() throws UserServiceException {
        final var instructionGroup = InstructionGroupDigest.builder()
                .withInstructionGroupNumber(1)
                .withInstructions(List.of(step1, step2)).build();
        final var recipe = RecipeDigest.builder()
                .withName(NAME)
                .withSeason(SeasonType.SPRING)
                .withIntroduction(INTRODUCTION)
                .withPrepTimeMin(5)
                .withCookTimeMin(15)
                .withServingQty(4)
                .withServingUnit("serving")
                .withInstructionGroups(List.of(instructionGroup))
                .build();

        createRecipe(recipe);

        assertEquals(2, testRecipe.getInstructionGroups().get(0).getInstructions().size());

        testRecipe.setIntroduction("blah blah blah");
        testRecipe.getInstructionGroups().get(0).getInstructions().addAll(List.of(step3, step4));

        final var response = updateRecipe(testRecipe);

        assertEquals("blah blah blah", response.getIntroduction());
        assertEquals(4, response.getInstructionGroups().get(0).getInstructions().size());
    }

    private RecipeDigest updateRecipe(RecipeDigest testRecipe) throws UserServiceException {
        return UpdateRecipeHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withUserId(userId)
                .withRequest(testRecipe)
                .build().execute();
    }


}