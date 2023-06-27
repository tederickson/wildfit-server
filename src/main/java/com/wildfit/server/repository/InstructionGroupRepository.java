package com.wildfit.server.repository;

import java.util.List;

import com.wildfit.server.model.InstructionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionGroupRepository extends JpaRepository<InstructionGroup, Long> {
    List<InstructionGroup> findByRecipeId(Long recipeId);
}
