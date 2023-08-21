package com.wildfit.server.service.handler;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

@SuperBuilder(setterPrefix = "with")
public class DeleteMealHandler extends CommonMealHandler {
    private final Long mealId;
    private final String userId;

    public void execute() throws WildfitServiceException {
        validate();

        final var meal = mealRepository.findByIdAndUuid(mealId, userId);

        meal.ifPresent(mealRepository::delete);
    }

    protected void validate() throws WildfitServiceException {
        super.validate();

        if (mealId == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
        if (StringUtils.isAllBlank(userId)) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
