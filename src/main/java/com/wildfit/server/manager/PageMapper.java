package com.wildfit.server.manager;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageMapper {
    private PageMapper() {
    }

    public static Pageable map(Integer page, Integer pageSize) throws WildfitServiceException {
        if (page == null || page < 0) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PAGE_OFFSET);
        }
        if (pageSize == null || pageSize < 0 || pageSize > 100) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PAGE_SIZE);
        }
        return PageRequest.of(page, pageSize);
    }
}
