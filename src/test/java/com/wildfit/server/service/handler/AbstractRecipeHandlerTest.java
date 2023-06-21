package com.wildfit.server.service.handler;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.repository.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class AbstractRecipeHandlerTest extends AbstractHandlerTest {
    protected static Long userId;
    protected static RecipeDigest testRecipe;

    @Autowired
    protected RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        final var users = userRepository.findByEmail(EMAIL);

        if (CollectionUtils.isEmpty(users)) {
            final var user = User.builder()
                    .withStatus(UserStatus.FREE.getCode())
                    .withCreateDate(java.time.LocalDate.now())
                    .withPassword(PASSWORD)
                    .withEmail(EMAIL).build();
            final var dbUser = userRepository.save(user);

            userId = dbUser.getId();
        }
    }

    @AfterEach
    void tearDown() {
        if (testRecipe != null) {
            recipeRepository.deleteById(testRecipe.getId());
        }

        super.tearDown();
    }

    protected void createRecipe(RecipeDigest recipe) throws UserServiceException {
        testRecipe = CreateRecipeHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withUserId(userId)
                .withRequest(recipe)
                .build().execute();
    }
}
