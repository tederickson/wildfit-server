package com.wildfit.server.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RECIPE", indexes = {@Index(name = "season_idx", columnList = "season")})
public final class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    @Column(length = 600)
    private String introduction;
    private String name;
    private String season;

    private int prepTimeMin;
    private int cookTimeMin;
    private String servingUnit;
    private int servingQty;

    private LocalDateTime created;
    private LocalDateTime updated;

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
        return Objects.hash(id);
    }
}
