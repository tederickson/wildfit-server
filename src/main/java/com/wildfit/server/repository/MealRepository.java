package com.wildfit.server.repository;

import java.util.List;
import java.util.Optional;

import com.wildfit.server.model.Meal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends PagingAndSortingRepository<Meal, Long>, CrudRepository<Meal, Long> {
    List<Meal> findAllByUuidOrderByStartDateDesc(String userId, Pageable pageable);

    Optional<Meal> findByIdAndUuid(Long id, String userId);
}
