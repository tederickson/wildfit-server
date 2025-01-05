package com.wildfit.server.domain;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class RecipeSummaryDigestTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(RecipeSummaryDigest.class).verify();
    }
}