package com.wildfit.server.service;

import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public RecipeListDigest listBySeason(String season, Pageable pageable) throws UserServiceException {
        return RecipeListDigest.builder().build();
    }
}
