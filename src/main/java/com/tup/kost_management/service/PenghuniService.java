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

    // Mengakomodasi Use Case: Registrasi Data Penghuni
    public Penghuni registerPenghuni(Penghuni penghuni) {
        penghuni.setRole("PENGHUNI"); // Memastikan role terkunci di sistem
        return penghuniRepository.save(penghuni);
    }

    public void deletePenghuni(Long id) {
        penghuniRepository.deleteById(id);
    }
}
