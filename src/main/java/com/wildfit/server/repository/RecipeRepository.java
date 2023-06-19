package com.wildfit.server.repository;

import java.util.List;

import com.wildfit.server.model.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {
    List<Recipe> findAllBySeason(String season, Pageable pageable);

    List<Recipe> findAllBySeasonAndName(String season, String name, Pageable pageable);
}
