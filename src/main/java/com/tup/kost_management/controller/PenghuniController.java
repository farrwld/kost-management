package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Penghuni;
import com.tup.kost_management.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Tambahkan ini
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // Tambahkan ini
// ----------------------------------------------

@Controller
@RequestMapping("/penghuni")
public class PenghuniController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String tampilkanHalamanPenghuni(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (role == null)
            return "redirect:/login";

        if (!"ADMIN".equals(role)) {
            session.setAttribute("errorAkses", "Maaf, data seluruh penghuni hanya boleh diakses oleh Admin!");
            return "redirect:/";
        }

        // Ambil semua data user dari database
        model.addAttribute("daftarPenghuni", userRepository.findAll());
        return "penghuni-view";
    }

    // FORM ACTION: Tambah User/Penghuni Baru via Web
    @PostMapping("/tambah")
    public String tambahPenghuniWeb(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {

        Penghuni penghuni = new Penghuni();
        penghuni.setUsername(username);
        penghuni.setPassword(password);
        penghuni.setRole(role);

        userRepository.save(penghuni);
        return "redirect:/penghuni";
    }
}