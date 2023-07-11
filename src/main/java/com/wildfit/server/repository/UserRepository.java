package com.wildfit.server.repository;

import java.util.List;
import java.util.Optional;

import com.wildfit.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);

    Optional<User> findByUuid(String uuid);
}
