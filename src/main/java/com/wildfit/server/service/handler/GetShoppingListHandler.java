package com.wildfit.server.service.handler;

import com.wildfit.server.domain.ShoppingListDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.ShoppingList;
import com.wildfit.server.model.mapper.ShoppingListDigestMapper;
import com.wildfit.server.repository.ShoppingListRepository;
import lombok.Builder;

import java.util.Objects;

@Builder(setterPrefix = "with")
public class GetShoppingListHandler {
    private final ShoppingListRepository shoppingListRepository;

    private final String userId;

    public ShoppingListDigest execute() throws WildfitServiceException {
        validate();

        final ShoppingList shoppingList = shoppingListRepository.findByUuid(userId)
                .orElseThrow(() -> new WildfitServiceException(
                        WildfitServiceError.SHOPPING_LIST_NOT_FOUND));
        return ShoppingListDigestMapper.map(shoppingList);
    }

    protected void validate() throws WildfitServiceException {
        Objects.requireNonNull(shoppingListRepository, "shoppingListRepository");

        if (userId == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
