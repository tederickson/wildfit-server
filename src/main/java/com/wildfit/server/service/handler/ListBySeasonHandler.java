package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Season;
import com.wildfit.server.model.mapper.RecipeListMapper;
import com.wildfit.server.repository.RecipeRepository;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder(setterPrefix = "with")
public class ListBySeasonHandler {
    private final RecipeRepository recipeRepository;
    private final SeasonType season;
    private final Pageable pageable;

    public RecipeListDigest execute() throws WildfitServiceException {
        validate();

        return RecipeListMapper.map(recipeRepository.findAllBySeason(Season.map(season), pageable));
    }

    private void validate() throws WildfitServiceException {
        Objects.requireNonNull(recipeRepository, " recipeRepository");

        if (season == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
        if (pageable == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
