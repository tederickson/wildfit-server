package com.wildfit.server.repository;

import com.wildfit.server.model.Recipe1;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface Recipe1Repository extends PagingAndSortingRepository<Recipe1, Long>, CrudRepository<Recipe1, Long> {
}
