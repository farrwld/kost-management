package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Keluhan;
import com.tup.kost_management.entity.Penghuni;
import com.tup.kost_management.entity.User;
import com.tup.kost_management.repository.UserRepository;
import com.tup.kost_management.service.KeluhanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/keluhan")
public class KeluhanController {

    @Autowired
    private KeluhanService keluhanService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String tampilkanHalamanKeluhan(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        String username = (String) session.getAttribute("userName");

        if (role == null)
            return "redirect:/login";

        // Filter tampilan data keluhan berdasarkan role
        if ("ADMIN".equals(role)) {
            model.addAttribute("daftarKeluhan", keluhanService.getAllKeluhan());
        } else {
            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                model.addAttribute("daftarKeluhan", keluhanService.getKeluhanByPenghuni(userOpt.get().getIdUser()));
            }
        }

        model.addAttribute("roleUser", role);
        return "keluhan-view";
    }

    // Simpan keluhan baru dari web browser
    @PostMapping("/tambah")
    public String tambahKeluhanWeb(@RequestParam String deskripsi, @RequestParam String tglKeluhan, HttpSession session) {
        String username = (String) session.getAttribute("userName");
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            Keluhan keluhan = new Keluhan();
            keluhan.setDeskripsi(deskripsi);
            keluhan.setTglKeluhan(java.time.LocalDate.parse(tglKeluhan));

            // Set relasi ke objek penghuni yang sedang login
            Penghuni p = new Penghuni();
            p.setIdUser(userOpt.get().getIdUser());
            keluhan.setPenghuni(p);
            keluhanService.inputKeluhan(keluhan);
        }
        return "redirect:/keluhan";
    }

    // Update status keluhan oleh Admin via klik tombol
    @PostMapping("/status/{id}")
    public String updateStatusWeb(@PathVariable Long id, @RequestParam String statusBaru) {
        keluhanService.updateStatusMaintenance(id, statusBaru);
        return "redirect:/keluhan";
    }
}