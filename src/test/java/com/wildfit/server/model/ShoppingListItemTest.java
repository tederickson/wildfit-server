package com.wildfit.server.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

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
                      .suppress(Warning.NONFINAL_FIELDS)
                      .suppress(Warning.SURROGATE_KEY).verify();
    }
}