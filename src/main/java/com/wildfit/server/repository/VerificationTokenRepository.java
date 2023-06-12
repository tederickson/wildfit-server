package com.wildfit.server.repository;

import com.wildfit.server.model.User;
import com.wildfit.server.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByUser(User user);

    VerificationToken findByToken(String token);
}
