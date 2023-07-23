package com.wildfit.server.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringExcluding;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class InstructionGroupTest {

    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(InstructionGroup.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(InstructionGroup.class, hasValidGettersAndSetters());
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(InstructionGroup.class, hasValidBeanToStringExcluding("instructions"));
    }

    @Test
    public void equalsAndHashCode() {
        final var instruction = Instruction.builder().withId(15L).build();
        EqualsVerifier.simple().forClass(InstructionGroup.class)
                      .withPrefabValues(Instruction.class, instruction, Instruction.builder().build())
                      .suppress(Warning.NONFINAL_FIELDS)
                      .suppress(Warning.SURROGATE_KEY).verify();
    }
}