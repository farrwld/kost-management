package com.tup.kost_management.service;

import com.tup.kost_management.entity.Keluhan;
import com.tup.kost_management.entity.Penghuni; // Pastikan ini di-import
import com.tup.kost_management.repository.KeluhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KeluhanService {

    @Autowired
    private KeluhanRepository keluhanRepository;

    public List<Keluhan> getAllKeluhan() {
        return keluhanRepository.findAll();
    }

    public Keluhan inputKeluhan(Keluhan keluhan) {
        keluhan.setStatusPerbaikan("PENDING");
        return keluhanRepository.save(keluhan);
    }

    public List<Keluhan> getKeluhanByPenghuni(Long idUser) {
        return keluhanRepository.findByPenghuniIdUser(idUser);
    }

    public Keluhan updateStatusMaintenance(Long idKeluhan, String statusBaru) {
        Optional<Keluhan> keluhanOpt = keluhanRepository.findById(idKeluhan);
        if (keluhanOpt.isPresent()) {
            Keluhan keluhan = keluhanOpt.get();
            keluhan.setStatusPerbaikan(statusBaru);

            // --- PROSES SANITASI DATA SENSITIF USER ---
            if (keluhan.getPenghuni() != null) {
                // 1. Ambil ID penghuni yang asli dari database
                Long idPenghuniAsli = keluhan.getPenghuni().getIdUser();
                
                // 2. Buat objek penampung kosong yang baru
                Penghuni penghuniDibersihkan = new Penghuni();
                penghuniDibersihkan.setIdUser(idPenghuniAsli);
                
                // 3. Set objek kosong tersebut kembali ke keluhan
                keluhan.setPenghuni(penghuniDibersihkan);
            }
            // ------------------------------------------

            return keluhanRepository.save(keluhan);
        }
        throw new RuntimeException("Data keluhan tidak ditemukan.");
    }
}