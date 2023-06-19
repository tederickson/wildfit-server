package com.wildfit.server.manager;

import com.wildfit.server.domain.RecipeListDigest;
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
    @GetMapping("/{season}")
    public RecipeListDigest retrieveRecipesForSeason(@PathVariable(value = "season") String season,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize)
            throws UserServiceException {

        final var logMessage = String.join("|", "retrieveRecipesForSeason", season,
                page.toString(), pageSize.toString());
        log.info(logMessage);

        final var pageable = PageMapper.map(page, pageSize);

        return recipeService.listBySeason(season, pageable);
    }

    @ApiOperation(value = "Delete Recipe")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Recipe deleted"), //
            @ApiResponse(code = 400, message = "Recipe not found"),
            @ApiResponse(code = 401, message = "Not authorized to delete recipe")})
    @DeleteMapping("/{id}/userIds/{userId}")
    public void deleteRecipe(@PathVariable("id") Long id, @PathVariable("userId") Long userId) throws UserServiceException {
        final var logMessage = String.join("|", "deleteRecipe", id.toString(), userId.toString());
        log.info(logMessage);

        recipeService.deleteRecipe(id, userId);
    }
}
