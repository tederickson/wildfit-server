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
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

@Builder(setterPrefix = "with")
public class ListBySeasonAndNameHandler {
    private final Recipe1Repository recipe1Repository;
    private final SeasonType season;
    private final String recipeName;
    private final Pageable pageable;

    public RecipeListDigest execute() throws UserServiceException {
        validate();

        final var results = recipe1Repository.findAllBySeasonNameAndName(Season.map(season).name(),
                recipeName, pageable);

        return RecipeListMapper.map(results);
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(recipe1Repository, "recipe1Repository");

        if (season == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (recipeName == null || StringUtils.trimToNull(recipeName) == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (pageable == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
