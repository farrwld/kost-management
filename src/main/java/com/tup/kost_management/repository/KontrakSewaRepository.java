package com.tup.kost_management.repository;

import com.tup.kost_management.entity.KontrakSewa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KontrakSewaRepository extends JpaRepository<KontrakSewa, Long> {
}
