package com.pm.authservice.repository;

import com.pm.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    //Below method uses JPA to search the db for a user based on the user's email address
    Optional<User> findByEmail(String email);
//JPA will handle the SQL query to search the user in DB
}
