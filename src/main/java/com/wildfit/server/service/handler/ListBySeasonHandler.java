package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.Season;
import com.wildfit.server.model.mapper.RecipeListMapper;
import com.wildfit.server.repository.Recipe1Repository;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder(setterPrefix = "with")
public class ListBySeasonHandler {
    private final Recipe1Repository recipe1Repository;
    private final SeasonType season;
    private final Pageable pageable;

    public RecipeListDigest execute() throws UserServiceException {
        validate();

        return RecipeListMapper.map(recipe1Repository.findAllBySeasonName(Season.map(season).name(), pageable));
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(recipe1Repository, "recipe1Repository");

        if (season == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (pageable == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
