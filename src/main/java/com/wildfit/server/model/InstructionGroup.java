package com.wildfit.server.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INSTRUCTION_GROUP", indexes = {@Index(name = "recipe_idx1", columnList = "recipeId")})
public class InstructionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long recipeId; // Recipe.id

    private int instructionGroupNumber;
    private String name;

    @OneToMany(mappedBy = "instructionGroup", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Instruction> instructions;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InstructionGroup that = (InstructionGroup) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return "InstructionGroup{" +
                "id=" + id +
                ", recipeId=" + recipeId +
                ", instructionGroupNumber=" + instructionGroupNumber +
                ", name='" + name + '\'' +
                '}';
    }

    public void deleteInstruction(Instruction entity) {
        instructions.remove(entity);
    }
}
