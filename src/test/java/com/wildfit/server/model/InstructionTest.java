package com.wildfit.server.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringExcluding;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class InstructionTest {

    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(Instruction.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(Instruction.class, hasValidGettersAndSetters());
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(Instruction.class, hasValidBeanToStringExcluding("instructionGroup"));
    }

    @Test
    public void equalsAndHashCode() {
        final var instructionGroup = InstructionGroup.builder().withId(15L).withName("Name").build();
        EqualsVerifier.simple().forClass(Instruction.class)
                      .withPrefabValues(InstructionGroup.class, instructionGroup, InstructionGroup.builder().build())
                      .suppress(Warning.NONFINAL_FIELDS)
                      .suppress(Warning.SURROGATE_KEY).verify();
    }
}