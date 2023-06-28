package com.wildfit.server.manager;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(description = "Recipe API")
@RequestMapping("v1/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @ApiOperation(value = "Retrieve recipes for a specific season")
    @GetMapping(value = "/seasons/{season}", produces = "application/json")
    public RecipeListDigest retrieveRecipesForSeason(@PathVariable(value = "season") SeasonType season,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize)
            throws UserServiceException {

        final var logMessage = String.join("|", "retrieveRecipesForSeason", season.toString(),
                page.toString(), pageSize.toString());
        log.info(logMessage);

        final var pageable = PageMapper.map(page, pageSize);

        return recipeService.listBySeason(season, pageable);
    }

    @ApiOperation(value = "Retrieve recipe")
    @GetMapping(value = "/{id}", produces = "application/json")
    public RecipeDigest retrieveRecipe(@PathVariable(value = "id") Long id) throws UserServiceException {

        final var logMessage = String.join("|", "retrieveRecipe", id.toString());
        log.info(logMessage);

        return recipeService.retrieveRecipe(id);
    }

    @ApiOperation(value = "Delete Recipe")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Recipe deleted"), //
            @ApiResponse(code = 404, message = "Recipe not found"),
            @ApiResponse(code = 401, message = "Not authorized to delete recipe")})
    @DeleteMapping(value = "/{id}/userIds/{userId}")
    public void deleteRecipe(@PathVariable("id") Long id, @PathVariable("userId") Long userId) throws UserServiceException {
        final var logMessage = String.join("|", "deleteRecipe", id.toString(), userId.toString());
        log.info(logMessage);

        recipeService.deleteRecipe(id, userId);
    }

    @ApiOperation(value = "Create Recipe")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created recipe", response = RecipeDigest.class)})
    @PostMapping(value = "/userIds/{userId}", produces = "application/json")
    public RecipeDigest createRecipe(@PathVariable("userId") Long userId,
                                     @RequestBody RecipeDigest request) throws UserServiceException {
        final var logMessage = String.join("|", "createRecipe", userId.toString(), request.toString());
        log.info(logMessage);

        return recipeService.createRecipe(userId, request);
    }

    @ApiOperation(value = "Update Recipe")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Recipe updated"), //
            @ApiResponse(code = 404, message = "Recipe not found"),
            @ApiResponse(code = 401, message = "Not authorized to update recipe")})
    @PutMapping(value = "/{id}/userIds/{userId}", produces = "application/json")
    public RecipeDigest updateRecipe(@PathVariable("id") Long id,
                                     @PathVariable("userId") Long userId,
                                     @RequestBody RecipeDigest request) throws UserServiceException {
        final var logMessage = String.join("|", "updateRecipe",
                id.toString(), userId.toString(), request.toString());
        log.info(logMessage);

        if (!id.equals(request.getId())) {
            log.error(id + " does not match " + request.getId());
            throw new UserServiceException(com.wildfit.server.exception.UserServiceError.INVALID_PARAMETER);
        }

        return recipeService.updateRecipe(userId, request);
    }

    @ApiOperation(value = "Add ingredient to the recipe")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added ingredient", response = IngredientDigest.class),
            @ApiResponse(code = 404, message = "Recipe not found"),
            @ApiResponse(code = 401, message = "Not authorized to update recipe")})
    @PostMapping(value = "/{id}/userIds/{userId}/recipeGroups/{recipeGroup}", produces = "application/json")
    public IngredientDigest createRecipeIngredient(@PathVariable("id") Long id,
                                                   @PathVariable("userId") Long userId,
                                                   @PathVariable("recipeGroup") Long recipeGroup,
                                                   @RequestBody IngredientDigest request) throws UserServiceException {
        final var logMessage = String.join("|", "createRecipeIngredient",
                id.toString(), userId.toString(), recipeGroup.toString(), request.toString());
        log.info(logMessage);

        return recipeService.createRecipeIngredient(userId, id, recipeGroup, request);
    }
}
