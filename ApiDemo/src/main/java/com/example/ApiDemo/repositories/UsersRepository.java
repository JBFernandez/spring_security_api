package com.example.ApiDemo.repositories;

import com.example.ApiDemo.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM users u WHERE u.email=?1")
    Optional<Users> findByEmail( String email );


}
