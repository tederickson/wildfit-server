package com.wildfit.server.domain;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static com.wildfit.server.domain.SeasonType.FALL;
import static com.wildfit.server.domain.SeasonType.SPRING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

@JsonTest
class RecipeDigestTest {
    @Autowired
    private JacksonTester<RecipeDigest> json;

    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(RecipeDigest.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(RecipeDigest.class, hasValidGettersAndSetters());
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(RecipeDigest.class, hasValidBeanToString());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(RecipeDigest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var request = RecipeDigest.builder().withSeason(FALL).build();
        assertEquals(FALL, request.getSeason());
    }

    @Test
    void jsonRead() throws Exception {
        final var introduction = "Tuna is one of the staples in our household. \nWe eat it all the time, because " +
                "it is simple and can be eaten for breakfast, lunch, dinner or snack.";

        final var recipeDigest = RecipeDigest.builder()
                                             .withName("Tuna salad")
                                             .withSeason(SPRING)
                                             .withPrepTimeMin(15)
                                             .withCookTimeMin(0)
                                             .withServingUnit("serving")
                                             .withServingQty(4)
                                             .withIntroduction(introduction)
                                             .build();

        final JsonContent<RecipeDigest> result = json.write(recipeDigest);
        final var jsonText = result.getJson();

        final RecipeDigest deserialized = json.parse(jsonText).getObject();

        assertEquals(recipeDigest, deserialized);
    }
}