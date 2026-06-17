package com.tup.kost_management.service;

import com.tup.kost_management.entity.Tagihan;
import com.tup.kost_management.repository.TagihanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TagihanService {

    @Autowired
    private TagihanRepository tagihanRepository;

    // Admin: Mengelola/Melihat semua tagihan masuk
    public List<Tagihan> getAllTagihan() {
        return tagihanRepository.findAll();
    }

    // Admin: Membuat tagihan baru bulanan
    public Tagihan buatTagihan(Tagihan tagihan) {
        return tagihanRepository.save(tagihan);
    }

    // Penghuni: Melihat tagihan milik dirinya sendiri
    public List<Tagihan> getTagihanByPenghuni(Long idUser) {
        return tagihanRepository.findByPenghuniIdUser(idUser);
    }

    // Penghuni: Membayar Tagihan & Include Generate Invoice (Simulasi status berubah)
    public Tagihan bayarTagihan(Long idTagihan) {
        Optional<Tagihan> tagihanOpt = tagihanRepository.findById(idTagihan);
        if (tagihanOpt.isPresent()) {
            Tagihan tagihan = tagihanOpt.get();
            tagihan.setStatusBayar("LUNAS"); // Mengubah status
            return tagihanRepository.save(tagihan); // Mengembalikan data sebagai objek "Invoice"
        }
        throw new RuntimeException("Tagihan tidak ditemukan dengan ID: " + idTagihan);
    }
}
