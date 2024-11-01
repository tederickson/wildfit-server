package com.wildfit.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@PrimaryKeyJoinColumn(name = CommonRecipe.JOIN_KEY)
public class Instruction extends CommonRecipe {
    @Id
    private Long id;  // shows up in database as CommonRecipe.JOIN_KEY
    private Integer stepNumber;

    @Column(length = 600, nullable = false)
    private String text;

    public Instruction() {
        super();
        setType(CommonRecipeType.INSTRUCTION);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Instruction that = (Instruction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
