package com.rayllanderson.springboot2.repositories;

import com.rayllanderson.springboot2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
