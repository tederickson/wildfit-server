package com.wildfit.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CommonRecipe {
    private final static String SEQUENCE_NAME = "COMMON_RECIPE";
    protected final static String JOIN_KEY = SEQUENCE_NAME + "_JOIN_ID";
    private final static String GENERATOR_NAME = SEQUENCE_NAME + "_generator";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME + "_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "common_recipe_group_id", referencedColumnName = "id", nullable = false)
    private RecipeGroup1 recipeGroup;

    @Column(length = 20, nullable = false)
    private String type;

    public void setType(CommonRecipeType type) {
        this.type = type.name();
    }

    public CommonRecipeType getTypeAsCommonRecipeType() {
        return CommonRecipeType.valueOf(type);
    }
}
