package com.tup.kost_management.service;

import com.tup.kost_management.entity.Keluhan;
import com.tup.kost_management.repository.KeluhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KeluhanService {

    @Autowired
    private KeluhanRepository keluhanRepository;

    // Admin: Mengelola Maintenance (Melihat seluruh keluhan masuk)
    public List<Keluhan> getAllKeluhan() {
        return keluhanRepository.findAll();
    }

    // Penghuni: Menginput Keluhan baru
    public Keluhan inputKeluhan(Keluhan keluhan) {
        keluhan.setStatusPerbaikan("PENDING"); // Default saat keluhan baru dibuat
        return keluhanRepository.save(keluhan);
    }

    // Penghuni: Melihat riwayat keluhannya sendiri
    public List<Keluhan> getKeluhanByPenghuni(Long idUser) {
        return keluhanRepository.findByPenghuniIdUser(idUser);
    }

    // Admin: Mengubah status perbaikan (Mengelola Maintenance)
    public Keluhan updateStatusMaintenance(Long idKeluhan, String statusBaru) {
        Optional<Keluhan> keluhanOpt = keluhanRepository.findById(idKeluhan);
        if (keluhanOpt.isPresent()) {
            Keluhan keluhan = keluhanOpt.get();
            keluhan.setStatusPerbaikan(statusBaru); // Misal diset ke "DIPROSES" atau "SELESAI"
            return keluhanRepository.save(keluhan);
        }
        throw new RuntimeException("Data keluhan tidak ditemukan.");
    }
}
