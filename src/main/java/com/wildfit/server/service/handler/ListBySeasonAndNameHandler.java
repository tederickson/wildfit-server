package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Season;
import com.wildfit.server.model.mapper.RecipeListMapper;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

@Builder(setterPrefix = "with")
public class ListBySeasonAndNameHandler {
    private final com.wildfit.server.repository.RecipeRepository recipeRepository;
    private final SeasonType season;
    private final String recipeName;
    private final Pageable pageable;

    public RecipeListDigest execute() throws WildfitServiceException {
        validate();

        final var results = recipeRepository.findAllBySeasonAndName(Season.map(season),
                recipeName, pageable);

        return RecipeListMapper.map(results);
    }

    private void validate() throws WildfitServiceException {
        Objects.requireNonNull(recipeRepository, " recipeRepository");

        if (season == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
        if (recipeName == null || StringUtils.trimToNull(recipeName) == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
        if (pageable == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
