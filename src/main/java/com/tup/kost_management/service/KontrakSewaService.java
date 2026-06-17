// package com.tup.kost_management.entity; // pastikan import Kamar tetap aman
package com.tup.kost_management.service;

import com.tup.kost_management.entity.KontrakSewa;
import com.tup.kost_management.entity.Kamar;
import com.tup.kost_management.repository.KontrakSewaRepository;
import com.tup.kost_management.repository.KamarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class KontrakSewaService {

    @Autowired
    private KontrakSewaRepository kontrakSewaRepository;

    @Autowired
    private KamarRepository kamarRepository;

    public List<KontrakSewa> getAllKontrak() {
        return kontrakSewaRepository.findAll();
    }

    // Menggunakan @Transactional agar jika salah satu proses gagal, database di-rollback
    @Transactional
    public KontrakSewa buatKontrakBaru(KontrakSewa kontrak) {
        // 1. Ambil data kamar yang disewa
        Kamar kamar = kontrak.getKamar();
        
        // 2. Ubah status kamar menjadi TERISI
        kamar.setStatus("TERISI");
        kamarRepository.save(kamar);
        
        // 3. Simpan kontrak sewa
        return kontrakSewaRepository.save(kontrak);
    }
}