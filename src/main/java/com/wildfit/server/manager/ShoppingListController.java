package com.wildfit.server.manager;

import com.wildfit.server.domain.CreateShoppingListRequest;
import com.wildfit.server.domain.ShoppingListDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.service.ShoppingListService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Shopping List API")
@RequestMapping("v1/shopping-list")
public class ShoppingListController {
    private final ShoppingListService shoppingListService;

    @Operation(summary = "Get the shopping list for the user")
    @GetMapping(value = "/users/{userId}", produces = "application/json")
    public ShoppingListDigest getShoppingList(@PathVariable String userId)
            throws WildfitServiceException {
        return shoppingListService.getShoppingList(userId);
    }

    @Operation(summary = "Delete an item from the shopping list")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Item deleted"), //
            @ApiResponse(responseCode = "404", description = "Shopping list not found")})
    @DeleteMapping(value = "/users/{userId}/items/{itemId}")
    public void deleteItemFromShoppingList(@PathVariable String userId, @PathVariable Long itemId)
            throws WildfitServiceException {
        shoppingListService.deleteItemFromShoppingList(userId, itemId);
    }

    @Operation(summary = "Create the shopping list from a meal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created shopping list"),
            @ApiResponse(responseCode = "404", description = "Meal not found")})
    @PostMapping(produces = "application/json")
    public ShoppingListDigest createShoppingList(@RequestBody CreateShoppingListRequest request)
            throws WildfitServiceException {
        shoppingListService.createShoppingList(request);

        return shoppingListService.getShoppingList(request.uuid());
    }

    @Operation(summary = "Update the shopping list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated shopping list"),
            @ApiResponse(responseCode = "404", description = "Shopping list not found")})
    @PostMapping(value = "/users/{userId}", produces = "application/json")
    public ShoppingListDigest updateShoppingList(@PathVariable String userId,
                                                 @RequestBody ShoppingListDigest request)
            throws WildfitServiceException {
        if (!userId.equals(request.uuid())) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
        shoppingListService.updateShoppingList(request);

        return shoppingListService.getShoppingList(userId);
    }

}
