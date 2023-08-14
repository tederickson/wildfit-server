package com.wildfit.server.repository;

import java.util.List;

import com.wildfit.server.model.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long>, CrudRepository<Recipe, Long> {
    List<com.wildfit.server.model.Recipe> findAllBySeasonName(String seasonName, Pageable pageable);

    List<com.wildfit.server.model.Recipe> findAllBySeasonNameAndName(String seasonName, String name, Pageable pageable);

    @Query(value = "SELECT r.* " +
            "FROM recipe r, recipe_group rg, ingredient ing, common_recipe cr " +
            "WHERE r.season=?1 " +
            "AND r.id = rg.recipe_id " +
            "AND rg.id = cr.common_recipe_group_id " +
            "AND cr.id = ing.common_recipe_join_id " +
            "AND ing.food_name =?2", nativeQuery = true)
    List<com.wildfit.server.model.Recipe> findAllBySeasonAndIngredientName(String season, String ingredientName,
                                                                           Pageable pageable);
}
