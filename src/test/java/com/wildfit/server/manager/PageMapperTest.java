package com.wildfit.server.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.Test;

class PageMapperTest {

    @Test
    void invalidPage() {
        final var exception = assertThrows(UserServiceException.class,
                () -> PageMapper.map(null, 23));
        assertEquals(UserServiceError.INVALID_PAGE_OFFSET, exception.getError());
    }

    @Test
    void invalidPageSize() {
        final var exception = assertThrows(UserServiceException.class,
                () -> PageMapper.map(1, null));
        assertEquals(UserServiceError.INVALID_PAGE_SIZE, exception.getError());
    }

    @Test
    void map() throws UserServiceException {
        assertNotNull(PageMapper.map(0, 49));
    }
}