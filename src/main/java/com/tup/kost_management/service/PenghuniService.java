package com.tup.kost_management.service;

import com.tup.kost_management.entity.Penghuni;
import com.tup.kost_management.repository.PenghuniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PenghuniService {

    @Autowired
    private PenghuniRepository penghuniRepository;

    public List<Penghuni> getAllPenghuni() {
        return penghuniRepository.findAll();
    }

    public Optional<Penghuni> getPenghuniById(Long id) {
        return penghuniRepository.findById(id);
    }

    public Penghuni registerPenghuni(Penghuni penghuni) {
        penghuni.setRole("PENGHUNI");
        penghuni.setAktif(true); // Akun baru otomatis diatur AKTIF
        return penghuniRepository.save(penghuni);
    }

    public List<Penghuni> getPenghuniAktif() {
        return penghuniRepository.findByIsAktifTrue();
    }

    public void nonaktifkanPenghuni(Long id) {
        penghuniRepository.findById(id).ifPresent(p -> {
            p.setAktif(false);
            penghuniRepository.save(p);
        });
    }

    public void aktifkanPenghuni(Long id) {
        penghuniRepository.findById(id).ifPresent(p -> {
            p.setAktif(true);
            penghuniRepository.save(p);
        });
    }
}