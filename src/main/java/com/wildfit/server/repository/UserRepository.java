package com.wildfit.server.repository;

import java.util.List;

import com.wildfit.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserName(String userName);
}
