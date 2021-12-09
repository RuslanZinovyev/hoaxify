package com.hoaxify.hoaxify.repository;

import com.hoaxify.hoaxify.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
