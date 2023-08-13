package com.wildfit.server.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Table(name = "RECIPE", indexes = {@Index(name = "RECIPE_season_idx", columnList = "season")})
public final class Recipe {
    final static String SEQUENCE_NAME = "RECIPE";
    final static String GENERATOR_NAME = SEQUENCE_NAME + "_generator";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME + "_seq", allocationSize = 1)
    private Long id;

    private String email;

    @Column(length = 600)
    private String introduction;

    private String name;

    @Column(name = "season", length = 10)
    private String seasonName;

    private int prepTimeMin;
    private int cookTimeMin;

    @Column(length = 10)
    private String servingUnit;
    private int servingQty;

    private LocalDateTime created;
    private LocalDateTime updated;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeGroup> recipeGroups;

    public void add(RecipeGroup recipeGroup) {
        if (recipeGroups == null) {
            recipeGroups = new ArrayList<>();
        }
        recipeGroups.add(recipeGroup);
    }

    public Recipe setSeasonName(String seasonName) {
        this.seasonName = seasonName;
        return this;
    }

    public Recipe setSeasonName(Season season) {
        this.seasonName = season.name();
        return this;
    }

    public void assignAllParents() {
        if (recipeGroups != null) {
            for (var recipeGroup : recipeGroups) {
                recipeGroup.setRecipe(this);

                if (recipeGroup.getCommonRecipes() != null) {
                    for (var commonRecipe : recipeGroup.getCommonRecipes()) {
                        commonRecipe.setRecipeGroup(recipeGroup);
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final var size = recipeGroups == null ? 0 : recipeGroups.size();
        return "Recipe1{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", name='" + name + '\'' +
                ", season='" + seasonName + '\'' +
                ", prepTimeMin=" + prepTimeMin +
                ", cookTimeMin=" + cookTimeMin +
                ", servingUnit='" + servingUnit + '\'' +
                ", servingQty=" + servingQty +
                ", created=" + created +
                ", updated=" + updated +
                ", recipeGroups size=" + size +
                '}';
    }
}
