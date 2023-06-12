package com.wildfit.server.repository;

import com.wildfit.server.model.User;
import com.wildfit.server.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUser(User user);
}
