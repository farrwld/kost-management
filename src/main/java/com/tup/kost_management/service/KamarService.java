package com.tup.kost_management.service;

import com.tup.kost_management.entity.Kamar;
import com.tup.kost_management.repository.KamarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KamarService {

    @Autowired
    private KamarRepository kamarRepository;

    public List<Kamar> getAllKamar() {
        return kamarRepository.findAll();
    }

    public Optional<Kamar> getKamarById(Long id) {
        return kamarRepository.findById(id);
    }

    public Kamar saveKamar(Kamar kamar) {
        return kamarRepository.save(kamar);
    }

    // Mengubah status menjadi NON_AKTIF (Pengganti Delete)
    public void nonaktifkanKamar(Long id) {
        kamarRepository.findById(id).ifPresent(kamar -> {
            kamar.setStatus("NON AKTIF");
            kamarRepository.save(kamar);
        });
    }

    // Mengembalikan status kamar NON_AKTIF menjadi TERSEDIA kembali
    public void aktifkanKamar(Long id) {
        kamarRepository.findById(id).ifPresent(kamar -> {
            kamar.setStatus("TERSEDIA");
            kamarRepository.save(kamar);
        });
    }

    public List<Kamar> getKamarTersedia() {
        return kamarRepository.findByStatus("TERSEDIA");
    }
}