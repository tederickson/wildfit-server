package com.wildfit.server.model;

import java.util.Objects;

import com.wildfit.server.domain.IngredientType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ShoppingListItem {
    final static String SEQUENCE_NAME = "SHOPPING_LIST_ITEM";
    final static String GENERATOR_NAME = SEQUENCE_NAME + "_generator";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME + "_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id", referencedColumnName = "id", nullable = false)
    private ShoppingList shoppingList;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "serving_qty")
    private Float servingQty;

    @Column(name = "serving_unit", length = 20)
    private String servingUnit;

    @Column(name = "ingredient_type", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private IngredientType ingredientType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShoppingListItem that = (ShoppingListItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ShoppingListItem{" +
                "id=" + id +
                ", foodName='" + foodName + '\'' +
                ", servingQty=" + servingQty +
                ", servingUnit='" + servingUnit + '\'' +
                ", ingredientType=" + ingredientType +
                '}';
    }
}
