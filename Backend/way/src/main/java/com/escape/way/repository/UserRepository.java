package com.escape.way.repository;

import com.escape.way.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUserId(String userId);

    boolean existsByUserId(String userId);
}
