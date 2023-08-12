package com.wildfit.server.repository;

import java.util.List;

import com.wildfit.server.model.Recipe1;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface Recipe1Repository extends PagingAndSortingRepository<Recipe1, Long>, CrudRepository<Recipe1, Long> {
    List<Recipe1> findAllBySeasonName(String seasonName, Pageable pageable);

    List<Recipe1> findAllBySeasonNameAndName(String seasonName, String name, Pageable pageable);

    @Query(value = "SELECT r.* " +
            "FROM recipe_1 r, recipe_group_1 rg, ingredient ing, common_recipe cr " +
            "WHERE r.season=?1 " +
            "AND r.id = rg.recipe_id " +
            "AND rg.id = cr.common_recipe_group_id " +
            "AND cr.id = ing.common_recipe_join_id " +
            "AND ing.food_name =?2", nativeQuery = true)
    List<Recipe1> findAllBySeasonAndIngredientName(String season, String ingredientName, Pageable pageable);
}
