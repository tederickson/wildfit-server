package com.wildfit.server.manager;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageMapper {
    private PageMapper() {
    }

    public static Pageable map(Integer page, Integer pageSize) throws UserServiceException {
        if (page == null || page < 0) {
            throw new UserServiceException(UserServiceError.INVALID_PAGE_OFFSET);
        }
        if (pageSize == null || pageSize < 0 || pageSize > 100) {
            throw new UserServiceException(UserServiceError.INVALID_PAGE_SIZE);
        }
        return PageRequest.of(page, pageSize);
    }
}
