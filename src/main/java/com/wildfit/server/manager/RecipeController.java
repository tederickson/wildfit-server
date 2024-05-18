package com.wildfit.server.manager;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exceptions are handled by the ManagerAdvice. The exception is logged
 * and converted to the appropriate HTTP Status code.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Recipe API")
@RequestMapping("v1/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @Operation(summary = "Retrieve recipes for a specific season")
    @GetMapping(value = "/seasons/{season}", produces = "application/json")
    public RecipeListDigest retrieveRecipesForSeason(@PathVariable(value = "season") SeasonType season,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize)
            throws WildfitServiceException {
        final var pageable = PageMapper.map(page, pageSize);

        return recipeService.listBySeason(season, pageable);
    }

    @Operation(summary = "Retrieve all recipes for a specific season that include a specific food")
    @GetMapping(value = "/seasons/{season}/ingredients/{ingredientName}", produces = "application/json")
    public RecipeListDigest listBySeasonAndIngredient(
            @PathVariable(value = "season") SeasonType season,
            @PathVariable(value = "ingredientName") String ingredientName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) throws WildfitServiceException {
        final var pageable = PageMapper.map(page, pageSize);

        return recipeService.listBySeasonAndIngredient(season, ingredientName, pageable);
    }

    @Operation(summary = "Retrieve all recipes for a specific season and name")
    @GetMapping(value = "/seasons/{season}/names/{recipeName}", produces = "application/json")
    public RecipeListDigest listBySeasonAndName(
            @PathVariable(value = "season") SeasonType season,
            @PathVariable(value = "recipeName") String recipeName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) throws WildfitServiceException {
        final var pageable = PageMapper.map(page, pageSize);

        return recipeService.listBySeasonAndName(season, recipeName, pageable);
    }

    @Operation(summary = "Retrieve recipe")
    @GetMapping(value = "/{recipeId}", produces = "application/json")
    public RecipeDigest retrieveRecipe(@PathVariable(value = "recipeId") Long id) throws WildfitServiceException {
        return recipeService.retrieveRecipe(id);
    }

    @Operation(summary = "Delete Recipe")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Recipe deleted"), //
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "401", description = "Not authorized to delete recipe")})
    @DeleteMapping(value = "/{recipeId}/users/{userId}")
    public void deleteRecipe(@PathVariable("recipeId") Long id, @PathVariable("userId") String userId)
            throws WildfitServiceException {
        recipeService.deleteRecipe(id, userId);
    }

    @Operation(summary = "Create Recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created recipe")})
    @PostMapping(value = "/users/{userId}", produces = "application/json")
    public RecipeDigest createRecipe(@PathVariable("userId") String userId,
                                     @RequestBody RecipeDigest request) throws WildfitServiceException {
        return recipeService.createRecipe(userId, request);
    }

    @Operation(summary = "Update Recipe")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Recipe updated"), //
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "401", description = "Not authorized to update recipe")})
    @PutMapping(value = "/{recipeId}/users/{userId}", produces = "application/json")
    public RecipeDigest updateRecipe(@PathVariable("recipeId") Long id,
                                     @PathVariable("userId") String userId,
                                     @RequestBody RecipeDigest request) throws WildfitServiceException {
        if (!id.equals(request.getId())) {
            log.error("{} does not match {}", id, request.getId());
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }

        return recipeService.updateRecipe(userId, request);
    }

}
