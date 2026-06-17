package com.tup.kost_management.repository;

import com.tup.kost_management.entity.Tagihan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TagihanRepository extends JpaRepository<Tagihan, Long> {
    // Menampilkan daftar tagihan berdasarkan id penghuni kos
    List<Tagihan> findByPenghuniIdUser(Long idUser);
}