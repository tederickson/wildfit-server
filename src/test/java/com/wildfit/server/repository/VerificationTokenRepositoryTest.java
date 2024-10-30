package com.wildfit.server.repository;

import com.wildfit.server.model.VerificationToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class VerificationTokenRepositoryTest extends AbstractRepositoryTest {

    private static final String TOKEN = "ApplePieToken3";

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Test
    public void findByToken_notFound() {
        assertNull(verificationTokenRepository.findByToken(TOKEN));
    }

    @Test
    public void findByToken() {
        final var dbUser = userRepository.save(USER);

        assertNotNull(dbUser);

        final var verificationToken = new VerificationToken(TOKEN, dbUser);
        verificationTokenRepository.save(verificationToken);

        final var dbVerificationToken = verificationTokenRepository.findByToken(TOKEN);
        assertNotNull(dbVerificationToken);
        assertEquals(dbUser, dbVerificationToken.getUser());
        assertEquals(TOKEN, dbVerificationToken.getToken());

        final var tomorrow = LocalDate.now().plusDays(1);
        assertEquals(tomorrow, dbVerificationToken.getExpiryDate().toLocalDate());
    }
}