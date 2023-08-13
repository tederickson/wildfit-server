package com.wildfit.server.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "RECIPE_GROUP")
public class RecipeGroup {
    private final static String SEQUENCE_NAME = "RECIPE_GROUP";
    private final static String GENERATOR_NAME = SEQUENCE_NAME + "_generator";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME + "_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)
    private Recipe recipe;

    @OneToMany(mappedBy = "recipeGroup", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommonRecipe> commonRecipes;

    private int recipeGroupNumber;
    private String name;

    @Override
    public String toString() {
        return "RecipeGroup1{" +
                "id=" + id +
                ", recipeGroupNumber=" + recipeGroupNumber +
                ", name='" + name + '\'' +
                '}';
    }

    public void add(CommonRecipe commonRecipe) {
        if (commonRecipes == null) {
            commonRecipes = new ArrayList<>();
        }
        commonRecipes.add(commonRecipe);
    }
}
