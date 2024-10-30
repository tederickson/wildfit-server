package com.wildfit.server.manager;

import com.wildfit.server.service.ReferenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

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