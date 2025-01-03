package com.wildfit.server.manager;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PageMapperTest {

    @Test
    void invalidPage() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> PageMapper.map(null, 23));
        assertEquals(WildfitServiceError.INVALID_PAGE_OFFSET, exception.getError());
    }

    @Test
    void invalidPageSize() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> PageMapper.map(1, null));
        assertEquals(WildfitServiceError.INVALID_PAGE_SIZE, exception.getError());
    }

    @Test
    void map() throws WildfitServiceException {
        assertNotNull(PageMapper.map(0, 49));
    }
}