package com.wildfit.server.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.UUID;

import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.model.VerificationToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(java.time.LocalDate.now())
                .withPassword(PASSWORD)
                .withUuid(UUID.randomUUID().toString())
                .withEmail(EMAIL).build();

        final var dbUser = userRepository.save(user);

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