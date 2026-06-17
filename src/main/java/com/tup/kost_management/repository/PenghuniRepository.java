package com.tup.kost_management.repository;

import com.tup.kost_management.entity.Penghuni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenghuniRepository extends JpaRepository<Penghuni, Long> {
}
