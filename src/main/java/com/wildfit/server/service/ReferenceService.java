package com.wildfit.server.service;

import com.wildfit.server.domain.IngredientType;
import com.wildfit.server.domain.ReferenceDigest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * This service provides a way to retrieve enum/database constant values.
 * This way an enum change does not break the UI.
 */
@Service
public class ReferenceService {

    public List<ReferenceDigest> getIngredientTypes() {
        return Arrays.stream(IngredientType.values()).map(this::map).toList();
    }

    private ReferenceDigest map(IngredientType ingredientType) {
        return ReferenceDigest.builder()
                .withDescription(ingredientType.getDescription())
                .withType(ingredientType.name())
                .build();
    }
}
