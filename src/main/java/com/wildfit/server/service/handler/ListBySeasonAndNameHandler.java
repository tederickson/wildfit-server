package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.Season;
import com.wildfit.server.model.mapper.RecipeListMapper;
import com.wildfit.server.repository.RecipeIngredientRepository;
import com.wildfit.server.repository.RecipeRepository;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

@Builder(setterPrefix = "with")
public class ListBySeasonAndNameHandler {
    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final SeasonType season;
    private final String recipeName;
    private final Pageable pageable;

    public RecipeListDigest execute() throws UserServiceException {
        validate();

        final var results = recipeRepository.findAllBySeasonAndName(Season.map(season).getCode(),
                recipeName, pageable);

        return RecipeListMapper.map(results);
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(recipeRepository, "recipeRepository");
        Objects.requireNonNull(recipeIngredientRepository, "recipeIngredientRepository");

        if (season == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (recipeName == null || StringUtils.trimToNull(recipeName) == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
