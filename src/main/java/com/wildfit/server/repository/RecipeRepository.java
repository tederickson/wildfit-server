package com.wildfit.server.repository;

import com.wildfit.server.model.Recipe;
import com.wildfit.server.model.Season;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long>, CrudRepository<Recipe, Long> {
    List<Recipe> findAllBySeason(Season season, Pageable pageable);

    List<Recipe> findAllBySeasonAndName(Season season, String name, Pageable pageable);

    @NativeQuery("SELECT r.* " +
            "FROM recipe r, recipe_group rg, ingredient ing, common_recipe cr " +
            "WHERE r.season=?1 " +
            "AND r.id = rg.recipe_id " +
            "AND rg.id = cr.common_recipe_group_id " +
            "AND cr.id = ing.common_recipe_join_id " +
            "AND ing.food_name =?2")
    List<Recipe> findAllBySeasonAndIngredientName(String seasonName, String ingredientName, Pageable pageable);
}
