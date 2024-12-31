package com.wildfit.server.service.handler;

import com.wildfit.server.domain.ShoppingListDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.ShoppingList;
import com.wildfit.server.model.ShoppingListItem;
import com.wildfit.server.repository.ShoppingListRepository;
import lombok.Builder;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Builder(setterPrefix = "with")
public class UpdateShoppingListHandler {
    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListDigest request;

    public void execute() throws WildfitServiceException {
        validate();

        final ShoppingList shoppingList = shoppingListRepository.findByUuid(request.uuid())
                .orElseThrow(() -> new WildfitServiceException(
                        WildfitServiceError.SHOPPING_LIST_NOT_FOUND));

        final Map<Long, ShoppingListItem> itemMap = shoppingList.getShoppingListItems().stream()
                .collect(Collectors.toMap(ShoppingListItem::getId,
                                          shoppingListItem -> shoppingListItem));

        for (var item : request.items()) {
            ShoppingListItem shoppingListItem = itemMap.get(item.id());

            if (shoppingListItem == null) {
                // Future requirement might add shopping list items
                throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
            }
            shoppingListItem.setPurchased(item.purchased());
            shoppingListItem.setServingQty(item.totalQuantity());
            shoppingListItem.setServingUnit(item.unit());
        }

        shoppingListRepository.save(shoppingList);
    }


    protected void validate() throws WildfitServiceException {
        Objects.requireNonNull(shoppingListRepository, "shoppingListRepository");

        if (request == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
