package com.tup.kost_management.repository;

import com.tup.kost_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Method untuk mencari user berdasarkan username secara dinamis
    Optional<User> findByUsername(String username);
}