package com.tup.kost_management.repository;

import com.tup.kost_management.entity.Tagihan;
import com.tup.kost_management.entity.KontrakSewa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TagihanRepository extends JpaRepository<Tagihan, Long> {
    
    // Menampilkan daftar tagihan berdasarkan id user penghuni lewat jalur kontrak
    List<Tagihan> findByKontrakSewaPenghuniIdUser(Long idUser);

    // Mengecek apakah kontrak ini sudah pernah memiliki lembar tagihan sebelumnya
    boolean existsByKontrakSewa(KontrakSewa kontrakSewa);
}