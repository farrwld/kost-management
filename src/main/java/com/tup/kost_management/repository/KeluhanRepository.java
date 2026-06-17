package com.tup.kost_management.repository;

import com.tup.kost_management.entity.Keluhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KeluhanRepository extends JpaRepository<Keluhan, Long> {
    List<Keluhan> findByPenghuniIdUser(Long idUser);
}
