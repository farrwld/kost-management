package com.tup.kost_management.repository;

import com.tup.kost_management.entity.Penghuni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PenghuniRepository extends JpaRepository<Penghuni, Long> {
    
    // Query otomatis Spring Data JPA untuk mengambil hanya Penghuni yang aktif
    List<Penghuni> findByIsAktifTrue();
}