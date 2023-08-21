package com.wildfit.server.repository;

import java.util.List;
import java.util.Optional;

import com.wildfit.server.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findAllByUuidOrderByStartDateDesc(String userId);

    Optional<Meal> findByIdAndUuid(Long id, String userId);
}
