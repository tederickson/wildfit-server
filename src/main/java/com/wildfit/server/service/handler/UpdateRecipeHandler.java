package com.wildfit.server.service.handler;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.InstructionGroup;
import com.wildfit.server.model.mapper.InstructionGroupMapper;
import com.wildfit.server.model.mapper.RecipeMapper;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder(setterPrefix = "with")
public class UpdateRecipeHandler {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    private final RecipeDigest request;
    private final Long userId;

    public RecipeDigest execute() throws UserServiceException {
        validate();

        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException(UserServiceError.USER_NOT_FOUND));
        final var recipe = recipeRepository.findById(request.getId())
                .orElseThrow(() -> new UserServiceException(UserServiceError.RECIPE_NOT_FOUND));

        if (recipe.getEmail().equals(user.getEmail())) {
            recipe.setIntroduction(request.getIntroduction());
            recipe.setName(request.getName());
            recipe.setSeason(request.getSeason().getCode());
            recipe.setPrepTimeMin(request.getPrepTimeMin());
            recipe.setCookTimeMin(request.getCookTimeMin());
            recipe.setServingUnit(request.getServingUnit());
            recipe.setServingQty(request.getServingQty());
            recipe.setUpdated(java.time.LocalDateTime.now());

            if (request.getInstructionGroups() == null) {
                recipe.setInstructionGroups(null);
            } else {
                Set<InstructionGroup> instructionGroups = new HashSet<>();

                for (var instructionGroupDigest : request.getInstructionGroups()) {
                    if (instructionGroupDigest.getId() == null) {
                        instructionGroups.add(InstructionGroupMapper.create(recipe, instructionGroupDigest));
                    } else {
                        final var instructionGroup = recipe.getInstructionGroups().stream()
                                .filter(x -> instructionGroupDigest.getId().equals(x.getId())).findFirst()
                                .orElse(null);
                        if (instructionGroup == null) {
                            log.error("Unable to find id " + instructionGroupDigest.getId());
                            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
                        }
                        instructionGroups.add(InstructionGroupMapper.update(instructionGroup, instructionGroupDigest));
                    }
                }

                recipe.setInstructionGroups(instructionGroups);
            }

            recipeRepository.save(recipe);
        } else {
            throw new UserServiceException(UserServiceError.NOT_AUTHORIZED);
        }

        return RecipeMapper.map(recipeRepository.save(recipe));
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(recipeRepository, "recipeRepository");

        if (userId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (request == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (request.getSeason() == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
