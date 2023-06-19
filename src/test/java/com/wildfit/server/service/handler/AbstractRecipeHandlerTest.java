package com.wildfit.server.service.handler;

import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class AbstractRecipeHandlerTest extends AbstractHandlerTest {
    protected static Long userId;

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
}
