package com.wildfit.server.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ShoppingListItemTest {

    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(ShoppingListItem.class, hasValidBeanConstructor());
    }

    @Test
    public void toStringOutput() {
        var shoppingList = new ShoppingListItem();
        assertNotNull(shoppingList.toString());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(ShoppingListItem.class)
                .withPrefabValues(ShoppingList.class, new ShoppingList().setId(3L), new ShoppingList().setId(13L))
                .suppress(Warning.SURROGATE_KEY).verify();
    }

    @Test
    public void accessParent() {
        ShoppingList shoppingList = new ShoppingList().setId(-157L);
        ShoppingListItem item = new ShoppingListItem().setId(-13L);

        shoppingList.setShoppingListItems(List.of(item));
        shoppingList.updateShoppingListItems();

        assertEquals(shoppingList.getId(), item.getShoppingList().getId());
    }
}