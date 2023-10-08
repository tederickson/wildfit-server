package com.wildfit.server.repository;

import java.util.Optional;

import com.wildfit.server.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    Optional<ShoppingList> findByUuid(String userId);

    void deleteByUuid(String userId);
}
