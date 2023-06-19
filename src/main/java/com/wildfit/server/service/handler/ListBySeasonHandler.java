package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.mapper.RecipeListMapper;
import com.wildfit.server.repository.RecipeRepository;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder(setterPrefix = "with")
public class ListBySeasonHandler {
    private final RecipeRepository recipeRepository;
    private final SeasonType season;
    private final Pageable pageable;

    public RecipeListDigest execute() throws UserServiceException {
        validate();

        return RecipeListMapper.map(recipeRepository.findAllBySeason(season.getCode(), pageable));
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(recipeRepository, "recipeRepository");

        if (season == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
