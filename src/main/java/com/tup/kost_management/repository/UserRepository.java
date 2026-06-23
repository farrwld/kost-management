package com.tup.kost_management.repository;

import com.tup.kost_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    // Hanya temukan user jika username cocok DAN status akunnya masih AKTIF
    Optional<User> findByUsernameAndIsAktifTrue(String username);
}