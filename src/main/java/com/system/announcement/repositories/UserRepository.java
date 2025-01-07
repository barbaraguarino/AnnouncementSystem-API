package com.system.announcement.repositories;

import com.system.announcement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByEmail(String email);

    Optional<User> getUserByEmail(String email);

}
