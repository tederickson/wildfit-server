package com.wildfit.server.service.handler;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Meal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DeleteMealHandlerTest extends CommonMealHandlerTest {

    @Test
    void execute() throws WildfitServiceException {
        final Meal meal = new Meal()
                .setUuid(userId)
                .setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now());
        final var entity = mealRepository.save(meal);

        mealService.deleteMeal(entity.getId(), userId);

        assertTrue(mealRepository.findById(entity.getId()).isEmpty());
    }

    @Test
    void execute_noMatchingRows() throws WildfitServiceException {
        mealService.deleteMeal(-1L, userId);
    }

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                     () -> DeleteMealHandler.builder().build().execute());
    }

    @Test
    void missingUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.deleteMeal(-1L, null));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void blankUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.deleteMeal(-1L, "  "));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingMealId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.deleteMeal(null, userId));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }
}