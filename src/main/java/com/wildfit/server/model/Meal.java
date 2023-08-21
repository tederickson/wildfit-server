package com.wildfit.server.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
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
@Table(name = "MEAL", indexes = {@Index(name = "user_idx", columnList = "uuid")})
public class Meal {
    final static String SEQUENCE_NAME = "MEAL";
    final static String GENERATOR_NAME = SEQUENCE_NAME + "_generator";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME + "_seq", allocationSize = 1)
    private Long id;

    private String uuid;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "meal", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealSummary> recipes;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Meal that = (Meal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", recipes=" + recipes +
                '}';
    }
}
