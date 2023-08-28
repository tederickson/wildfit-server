package com.wildfit.server.manager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wildfit.server.service.ReferenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReferenceControllerTest {

    @Mock
    private ReferenceService referenceService;

    private ReferenceController controller;

    @BeforeEach
    void setUp() {
        controller = new ReferenceController(referenceService);
    }

    @Test
    void getIngredientTypes() {
        assertTrue(controller.getIngredientTypes().isEmpty());
    }
}