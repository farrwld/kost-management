package com.tup.kost_management.controller;

import com.tup.kost_management.entity.User;
import com.tup.kost_management.repository.UserRepository;
import com.tup.kost_management.service.TagihanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("/tagihan")
public class TagihanController {

    @Autowired
    private TagihanService tagihanService;

    @Autowired
    private UserRepository userRepository; // Tambahkan ini untuk mencari ID user berdasarkan username session

    @GetMapping
    public String tampilkanHalamanTagihan(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        String username = (String) session.getAttribute("userName");

        if (role == null) {
            return "redirect:/login"; // Tendang ke login jika belum autentikasi
        }

        if ("ADMIN".equals(role)) {
            // Jika ADMIN: Tampilkan seluruh tagihan dari semua penghuni kost
            model.addAttribute("daftarTagihan", tagihanService.getAllTagihan());
        } else {
            // Jika PENGHUNI: Cari ID user-nya terlebih dahulu dari database berdasarkan username session
            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                Long idUserLogon = userOpt.get().getIdUser();
                // Filter hanya mengambil tagihan milik ID user yang sedang login saat ini
                model.addAttribute("daftarTagihan", tagihanService.getTagihanByPenghuni(idUserLogon));
            }
        }

        model.addAttribute("roleUser", role); // Opsional jika ingin menyembunyikan tombol aksi di HTML berdasarkan role
        return "tagihan-view";
    }
}