package com.example.LoginSpringBoot.Repository;

import com.example.LoginSpringBoot.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
