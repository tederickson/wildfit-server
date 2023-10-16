package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.ShoppingList;
import com.wildfit.server.repository.ShoppingListRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;

@Builder(setterPrefix = "with")
public class DeleteItemFromShoppingListHandler {
    private final UserRepository userRepository;
    private final ShoppingListRepository shoppingListRepository;

    private final String userId;
    private Long itemId;

    public void execute() throws WildfitServiceException {
        validate();

        userRepository.findByUuid(userId)
                      .orElseThrow(() -> new WildfitServiceException(WildfitServiceError.USER_NOT_FOUND));

        final ShoppingList shoppingList = shoppingListRepository.findByUuid(userId)
                                                                .orElseThrow(() -> new WildfitServiceException(
                                                                        WildfitServiceError.SHOPPING_LIST_NOT_FOUND));

        shoppingList.getShoppingListItems().removeIf(item -> itemId.equals(item.getId()));
        shoppingListRepository.save(shoppingList);
    }


    protected void validate() throws WildfitServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(shoppingListRepository, "shoppingListRepository");

        if (userId == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
        if (itemId == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
