package com.expensetracker.expenseTracker.repository;

import com.expensetracker.expenseTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);


}
