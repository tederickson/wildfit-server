package com.wildfit.server.repository;

import java.util.List;

import com.wildfit.server.model.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {
    List<Recipe> findAllBySeason(String season, Pageable pageable);

    List<Recipe> findAllBySeasonAndName(String season, String name, Pageable pageable);

    @Query(value = "SELECT r.* " +
            "FROM Recipe r, recipe_ingredient ri " +
            "WHERE r.season=?1 " +
            "AND r.id = ri.recipe_id " +
            "AND ri.food_name =?2", nativeQuery = true)
    List<Recipe> findAllBySeasonAndIngredientName(String season,
                                                  String ingredientName,
                                                  Pageable pageable);
}
