package com.hoaxify.hoaxify.repository;

import com.hoaxify.hoaxify.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    Page<User> findByUserNameNot(String userName, Pageable page);
}
