package com.wildfit.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

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
@Table(name = "RECIPE", indexes = {@Index(name = "season_idx", columnList = "season")})
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String introduction;
    private String name;
    private String season;

    private int prepTimeMin;
    private int cookTimeMin;
    private String servingUnit;
    private int servingQty;

    private LocalDateTime created;
    private LocalDateTime updated;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<InstructionGroup> instructionGroups;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RecipeIngredient> recipeIngredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Recipe recipe = (Recipe) o;
        return id == recipe.id;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", name='" + name + '\'' +
                ", season='" + season + '\'' +
                ", prepTimeMin=" + prepTimeMin +
                ", cookTimeMin=" + cookTimeMin +
                ", servingUnit='" + servingUnit + '\'' +
                ", servingQty=" + servingQty +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
