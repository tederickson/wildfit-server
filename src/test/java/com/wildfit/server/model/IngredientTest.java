package com.wildfit.server.model;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IngredientTest {

    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(Ingredient.class, hasValidBeanConstructor());
    }

    @Test
    public void testToString() {
        final var ingredient = new Ingredient().setId(3L).setFoodName("Orange").setDescription("Mandarin Orange");
        assertEquals("Ingredient(id=3, foodName=Orange, description=Mandarin Orange," +
                             " ingredientServingQty=null, ingredientServingUnit=null, ingredientType=null)",
                     ingredient.toString());
    }

    @Test
    public void equalsAndHashCode() {
        final Ingredient ingredient1 = new Ingredient().setId(15L);
        final Ingredient ingredient2 = new Ingredient().setId(11115L);
        final var dupe = new Ingredient().setId(ingredient2.getId()).setFoodName("Orange");

        assertNotEquals(ingredient2, ingredient1);
        assertEquals(ingredient2, ingredient2);
        assertEquals(ingredient2, dupe);
        assertNotEquals(ingredient2.hashCode(), ingredient1.hashCode());
        assertNotEquals(ingredient2, ingredient1.hashCode());
        assertEquals(ingredient2.hashCode(), dupe.hashCode());
    }
}