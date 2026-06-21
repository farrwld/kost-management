package com.tup.kost_management.repository;

import com.tup.kost_management.entity.KontrakSewa;
import com.tup.kost_management.entity.Penghuni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface KontrakSewaRepository extends JpaRepository<KontrakSewa, Long> {
    // Digunakan untuk mencari kontrak aktif saat proses pembayaran tagihan
    Optional<KontrakSewa> findByPenghuniAndStatusKontrak(Penghuni penghuni, String statusKontrak);
}