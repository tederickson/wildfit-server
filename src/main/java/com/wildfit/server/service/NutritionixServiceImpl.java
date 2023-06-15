package com.wildfit.server.service;

import com.wildfit.server.model.NutritionixHeaderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The service calls Handlers to implement the functionality.
 * This provides loose coupling, allows several people to work on the service without merge collisions
 * and provides a lightweight service.
 */

@Service
public class NutritionixServiceImpl implements NutritionixService {
    @Autowired
    private NutritionixHeaderInfo nutritionixHeaderInfo;
}
