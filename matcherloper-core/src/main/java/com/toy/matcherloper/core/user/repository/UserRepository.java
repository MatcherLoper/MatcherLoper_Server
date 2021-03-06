package com.toy.matcherloper.core.user.repository;

import com.toy.matcherloper.core.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
}
