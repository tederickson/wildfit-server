package com.wildfit.server.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                .withCreateDate(new Date())
                .withPassword(PASSWORD)
                .withEmail(EMAIL).build();

        final var dbUser = userRepository.save(user);

        assertNotNull(dbUser);

        final var verificationToken = new VerificationToken(TOKEN, dbUser);
        verificationTokenRepository.save(verificationToken);

        final var dbVerificationToken = verificationTokenRepository.findByToken(TOKEN);
        assertNotNull(dbVerificationToken);
        assertEquals(dbUser, dbVerificationToken.getUser());
        assertEquals(TOKEN, dbVerificationToken.getToken());

        final var calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);

        final var dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        final var tomorrowAsText = dateFormat.format(calendar.getTime());
        final var expirationAsText = dateFormat.format(dbVerificationToken.getExpiryDate());

        assertEquals(tomorrowAsText, expirationAsText);
    }
}