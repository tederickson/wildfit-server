package com.wildfit.server.manager;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.MealDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.service.MealService;
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

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Meal API")
@RequestMapping("v1/meals")
public class MealController {
    private final MealService mealService;

    @Operation(summary = "Get all meals for the user")
    @GetMapping(value = "/users/{userId}", produces = "application/json")
    public List<MealDigest> retrieveAllMeals(@PathVariable String userId,
                                             @RequestParam(defaultValue = "0") Integer page,
                                             @RequestParam(defaultValue = "30") Integer pageSize)
            throws WildfitServiceException {
        final var pageable = PageMapper.map(page, pageSize);

        return mealService.retrieveAllMeals(userId, pageable);
    }

    @Operation(summary = "Get meal details")
    @GetMapping(value = "/{mealId}/users/{userId}", produces = "application/json")
    public MealDigest retrieveMeal(@PathVariable Long mealId,
                                   @PathVariable String userId) throws WildfitServiceException {
        return mealService.retrieveMeal(mealId, userId);
    }

    @Operation(summary = "Delete a meal")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Meal deleted"), //
            @ApiResponse(responseCode = "404", description = "Meal not found"),
            @ApiResponse(responseCode = "401", description = "Not authorized to delete meal")})
    @DeleteMapping(value = "/{mealId}/users/{userId}")
    public void deleteMeal(@PathVariable Long mealId,
                           @PathVariable String userId) throws WildfitServiceException {
        mealService.deleteMeal(mealId, userId);
    }

    @Operation(summary = "Create new meal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created meal")})
    @PostMapping(produces = "application/json")
    public MealDigest createMeal(@RequestBody CreateMealRequest request) throws WildfitServiceException {
        return mealService.createMeal(request);
    }

    @Operation(summary = "Update meal")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Meal updated"), //
            @ApiResponse(responseCode = "404", description = "Meal not found"),
            @ApiResponse(responseCode = "401", description = "Not authorized to update meal")})
    @PutMapping(produces = "application/json")
    public MealDigest updateMeal(@RequestBody MealDigest request) throws WildfitServiceException {
        return mealService.updateMeal(request);
    }
}
