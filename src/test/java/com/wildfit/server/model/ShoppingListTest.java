package com.wildfit.server.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ShoppingListTest {
    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(ShoppingList.class, hasValidBeanConstructor());
    }

    @Test
    public void toStringOutput() {
        var shoppingList = new ShoppingList();
        assertNotNull(shoppingList.toString());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(ShoppingList.class)
                .withPrefabValues(ShoppingListItem.class,
                                  new ShoppingListItem().setId(3L),
                                  new ShoppingListItem().setId(13L))
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.SURROGATE_KEY).verify();
    }
}