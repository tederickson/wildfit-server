package com.wildfit.server.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class InstructionTest {

    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(Instruction.class, hasValidBeanConstructor());
    }

    @Test
    public void testToString() {
        final var instruction = new Instruction().setId(3L).setText("Bob").setStepNumber(1);
        assertEquals("Instruction(id=3, stepNumber=1, text=Bob)", instruction.toString());
    }

    @Test
    public void equalsAndHashCode() {
        final Instruction instruction1 = new Instruction().setId(15L);
        final Instruction instruction2 = new Instruction().setId(11115L);
        final var dupe = new Instruction().setId(instruction2.getId()).setText("Bob");

        assertNotEquals(instruction2, instruction1);
        assertEquals(instruction2, instruction2);
        assertEquals(instruction2, dupe);
        assertNotEquals(instruction2.hashCode(), instruction1.hashCode());
        assertEquals(instruction2.hashCode(), dupe.hashCode());
        assertFalse(instruction2.equals("bob"));
    }
}