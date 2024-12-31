package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class ShoppingListItemDigestTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(ShoppingListItemDigest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }
}