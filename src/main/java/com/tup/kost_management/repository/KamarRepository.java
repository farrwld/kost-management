package com.tup.kost_management.repository;

import com.tup.kost_management.entity.Kamar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KamarRepository extends JpaRepository<Kamar, Long> {
    // Sesuai use case diagram: Cek Ketersediaan Kamar
    List<Kamar> findByStatus(String status);
}
