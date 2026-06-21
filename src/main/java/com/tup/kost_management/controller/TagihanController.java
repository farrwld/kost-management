package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Penghuni;
import com.tup.kost_management.entity.Tagihan;
import com.tup.kost_management.entity.User;
import com.tup.kost_management.repository.UserRepository;
import com.tup.kost_management.service.TagihanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/tagihan")
public class TagihanController {

    @Autowired
    private TagihanService tagihanService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String tampilkanHalamanTagihan(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        String username = (String) session.getAttribute("userName");

        if (role == null) return "redirect:/login";

        if ("ADMIN".equals(role)) {
            List<Tagihan> semuaTagihan = tagihanService.getAllTagihan();
            semuaTagihan.removeIf(Objects::isNull);
            model.addAttribute("daftarTagihan", semuaTagihan);
            // Tambahkan list user ke view untuk isi dropdown menu di Modal Admin
            model.addAttribute("daftarUser", userRepository.findAll());
        } else {
            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                model.addAttribute("daftarTagihan", tagihanService.getTagihanByPenghuni(userOpt.get().getIdUser()));
            }
        }

        model.addAttribute("roleUser", role);
        return "tagihan-view";
    }

    // FORM ACTION 1: Admin Terbitkan Tagihan Baru
    @PostMapping("/tambah")
    public String tambahTagihanWeb(
            @RequestParam Long idUserPenghuni,
            @RequestParam String periode,
            @RequestParam Double jumlahTagihan,
            @RequestParam String jatuhTempo) {
        
        Tagihan tagihan = new Tagihan();
        tagihan.setPeriode(periode);
        tagihan.setJumlahTagihan(jumlahTagihan);
        tagihan.setJatuhTempo(LocalDate.parse(jatuhTempo));
        tagihan.setStatusBayar("BELUM_BAYAR"); // Default tagihan baru terbit

        Penghuni p = new Penghuni();
        p.setIdUser(idUserPenghuni);
        tagihan.setPenghuni(p);

        tagihanService.buatTagihan(tagihan);
        return "redirect:/tagihan";
    }

    // FORM ACTION 2: Simulasi Bayar Tagihan Langsung via Web (Pengganti PUT Postman)
    @PostMapping("/bayar/{id}")
    public String bayarTagihanWeb(@PathVariable Long id) {
        tagihanService.bayarTagihan(id); // Memanggil logika pelunasan service kamu
        return "redirect:/tagihan";
    }
}