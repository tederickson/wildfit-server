package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeGroupDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeleteRecipeHandlerTest extends CommonRecipeHandlerTest {
    final String name = "TEST TEST TEST";

    final InstructionDigest step1 = InstructionDigest.builder().withStepNumber(1).withInstruction("Heat the oil")
                                                     .build();

    final RecipeGroupDigest recipeGroupDigest = RecipeGroupDigest.builder()
                                                                 .withInstructionGroupNumber(1)
                                                                 .withInstructions(List.of(step1)).build();
    final RecipeDigest recipe = RecipeDigest.builder()
                                            .withName(name)
                                            .withSeason(SeasonType.FALL)
                                            .withIntroduction("introduction")
                                            .withPrepTimeMin(5)
                                            .withCookTimeMin(15)
                                            .withServingQty(4)
                                            .withServingUnit("serving")
                                            .withRecipeGroups(List.of(recipeGroupDigest))
                                            .build();

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> DeleteRecipeHandler.builder().build().execute());
    }

    @Test
    void missingId() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> DeleteRecipeHandler.builder()
                                         .withUserRepository(userRepository)
                                         .withRecipeRepository(recipeRepository)
                                         .withRecipeId(-1L)
                                         .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> DeleteRecipeHandler.builder()
                                         .withUserRepository(userRepository)
                                         .withRecipeRepository(recipeRepository)
                                         .withUserId("-14L")
                                         .withRecipeId(-1L)
                                         .build().execute());
        assertEquals(WildfitServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void userNotAuthorized() throws WildfitServiceException {
        createRecipe(recipe);

        final var bobEmail = "bob.bob@bob.test.net";
        assertNotSame(EMAIL, bobEmail);

        final var user = User.builder()
                             .withStatus(UserStatus.FREE.getCode())
                             .withCreateDate(LocalDate.now())
                             .withPassword("encoded password")
                             .withUuid(UUID.randomUUID().toString())
                             .withEmail(bobEmail).build();
        final var dbUser = userRepository.save(user);

        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> DeleteRecipeHandler.builder()
                                         .withUserRepository(userRepository)
                                         .withRecipeRepository(recipeRepository)
                                         .withUserId(dbUser.getUuid())
                                         .withRecipeId(testRecipe.getId())
                                         .build().execute());
        assertEquals(WildfitServiceError.NOT_AUTHORIZED, exception.getError());

        userRepository.delete(dbUser);
    }

    @Test
    void recipeNotFound() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> DeleteRecipeHandler.builder()
                                         .withUserRepository(userRepository)
                                         .withRecipeRepository(recipeRepository)
                                         .withUserId(userId)
                                         .withRecipeId(-100L)
                                         .build().execute());
        assertEquals(WildfitServiceError.RECIPE_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws WildfitServiceException {
        createRecipe(recipe);

        DeleteRecipeHandler.builder()
                           .withUserRepository(userRepository)
                           .withRecipeRepository(recipeRepository)
                           .withUserId(userId)
                           .withRecipeId(testRecipe.getId())
                           .build().execute();

        assertTrue(recipeRepository.findById(testRecipe.getId()).isEmpty());
    }
}