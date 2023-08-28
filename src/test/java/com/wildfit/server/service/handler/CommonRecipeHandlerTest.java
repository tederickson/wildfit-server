package com.wildfit.server.service.handler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.repository.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class CommonRecipeHandlerTest extends CommonHandlerTest {
    static final InstructionDigest step1 = InstructionDigest.builder().withStepNumber(1)
                                                            .withInstruction(
                                                                    "Heat the oil in a heavy frying pan over medium-high heat, then cook the beef " +
                                                                            "until it’s " +
                                                                            "cooked through and starting to brown, breaking apart with a turner as it cooks.")
                                                            .build();
    static final InstructionDigest step2 = InstructionDigest.builder().withStepNumber(2)
                                                            .withInstruction(
                                                                    "While beef cooks, mix together the fish sauce, chili sauce, and water in a " +
                                                                            "small bowl. Zest the skin " +
                                                                            "of the lime and squeeze the juice. (You may need two limes to get enough juice, " +
                                                                            "but only use " +
                                                                            "the zest from one lime.) Thinly slice the green onions and chop the cilantro. " +
                                                                            "Most iceberg lettuce does not need washing as long as you remove the outer leaves. " +
                                                                            "Cut out the core and cut the lettuce into quarters to make 'cups' to hold the beef " +
                                                                            "mixture.").build();
    static final InstructionDigest step3 = InstructionDigest.builder().withStepNumber(3)
                                                            .withInstruction(
                                                                    "When the beef is done, add the chili sauce mixture and let it sizzle until " +
                                                                            "the water has evaporated, " +
                                                                            "stirring a few times to get the flavor mixed through the meat. Turn off the heat and " +
                                                                            "stir in " +
                                                                            "the lime zest, lime juice, sliced green onions, and chopped cilantro.")
                                                            .build();
    static final InstructionDigest step4 = InstructionDigest.builder().withStepNumber(4)
                                                            .withInstruction(
                                                                    "Serve meat mixture with iceberg lettuce leaves. Fill with beef and wrap " +
                                                                            "around it. Eaten with your hands.")
                                                            .build();

    static final String INTRODUCTION = "These lettuce wraps are so easy and full of flavor! " +
            "They make a great side dish, or are perfect for a healthy spring approved recipe.";

    protected static String userId;
    protected static RecipeDigest testRecipe;
    protected static List<RecipeDigest> recipeDigests = new ArrayList<>();

    @Autowired
    protected RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        final var users = userRepository.findByEmail(EMAIL);

        if (CollectionUtils.isEmpty(users)) {

            final var user = User.builder()
                                 .withStatus(UserStatus.FREE.getCode())
                                 .withCreateDate(LocalDate.now())
                                 .withPassword("encoded password")
                                 .withUuid(UUID.randomUUID().toString())
                                 .withEmail(EMAIL).build();
            final var dbUser = userRepository.save(user);

            userId = dbUser.getUuid();
        } else {
            userId = users.get(0).getUuid();
        }
    }

    @AfterEach
    void tearDown() {
        if (testRecipe != null) {
            recipeRepository.deleteById(testRecipe.getId());

            testRecipe = null;
        }
        if (!recipeDigests.isEmpty()) {
            final var recipeIds = recipeDigests.stream().map(RecipeDigest::getId).collect(Collectors.toList());
            recipeRepository.deleteAllById(recipeIds);

            recipeDigests.clear();
        }

        super.tearDown();
    }

    protected void createRecipe(RecipeDigest recipe) throws WildfitServiceException {
        testRecipe = CreateRecipeHandler.builder()
                                        .withUserRepository(userRepository)
                                        .withRecipeRepository(recipeRepository)
                                        .withUserId(userId)
                                        .withRequest(recipe)
                                        .build().execute();
    }

    protected void createRecipes(List<RecipeDigest> recipes) throws WildfitServiceException {
        for (var recipe : recipes) {
            final var persisted = CreateRecipeHandler.builder()
                                                     .withUserRepository(userRepository)
                                                     .withRecipeRepository(recipeRepository)
                                                     .withUserId(userId)
                                                     .withRequest(recipe)
                                                     .build().execute();
            recipeDigests.add(persisted);
        }
    }
}
