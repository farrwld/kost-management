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

    @Transactional
    public KontrakSewa buatKontrakBaru(KontrakSewa kontrak) {
        // 1. Ambil data kamar utuh dari database berdasarkan ID yang dikirim Postman
        Long idKamarDarisewa = kontrak.getKamar().getIdKamar();
        Kamar kamarObjekUtuh = kamarRepository.findById(idKamarDarisewa)
                .orElseThrow(() -> new RuntimeException("Kamar dengan ID " + idKamarDarisewa + " tidak ditemukan"));
        
        // 2. Ubah status pada objek kamar yang datanya lengkap dari database
        kamarObjekUtuh.setStatus("TERISI");
        kamarRepository.save(kamarObjekUtuh);
        
        // 3. Pasang kembali objek kamar yang utuh ke objek kontrak sebelum disimpan
        kontrak.setKamar(kamarObjekUtuh);
        
        // 4. Simpan kontrak sewa
        return kontrakSewaRepository.save(kontrak);
    }
}