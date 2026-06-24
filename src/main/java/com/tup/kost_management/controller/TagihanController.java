package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Tagihan;
import com.tup.kost_management.entity.User;
import com.tup.kost_management.repository.UserRepository;
import com.tup.kost_management.service.TagihanService;
import com.tup.kost_management.service.KontrakSewaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private KontrakSewaService kontrakSewaService; // Ganti ke KontrakSewaService

    @GetMapping
    public String tampilkanHalamanTagihan(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        String username = (String) session.getAttribute("userName");

        if (role == null) return "redirect:/login";

        if ("ADMIN".equals(role)) {
            List<Tagihan> semuaTagihan = tagihanService.getAllTagihan();
            semuaTagihan.removeIf(Objects::isNull);
            model.addAttribute("daftarTagihan", semuaTagihan);
            
            // Kirim daftar kontrak yang AKTIF saja untuk dropdown form penerbitan
            List<com.tup.kost_management.entity.KontrakSewa> kontrakAktif = kontrakSewaService.getAllKontrak().stream()
                    .filter(c -> "AKTIF".equals(c.getStatusKontrak()))
                    .toList();
            model.addAttribute("daftarKontrak", kontrakAktif);
        } else {
            Optional<User> userOpt = userRepository.findByUsernameAndIsAktifTrue(username);
            if (userOpt.isPresent()) {
                model.addAttribute("daftarTagihan", tagihanService.getTagihanByPenghuni(userOpt.get().getIdUser()));
            }
        }

        model.addAttribute("roleUser", role);
        return "tagihan-view";
    }

    @PostMapping("/tambah")
    public String tambahTagihanWeb(
            @RequestParam Long idKontrak, // Ubah dari idUserPenghuni
            @RequestParam String periode,
            @RequestParam String jatuhTempo) {
        
        // Panggil service pintar yang otomatis menghitung Logika A
        tagihanService.buatTagihanOtomatis(idKontrak, periode, jatuhTempo);
        return "redirect:/tagihan";
    }

    @PostMapping("/bayar/{id}")
    public String bayarTagihanWeb(@PathVariable Long id) {
        tagihanService.bayarTagihan(id);
        return "redirect:/tagihan";
    }

    @GetMapping("/cetak/{id}")
    public void cetakInvoiceWeb(@PathVariable Long id, jakarta.servlet.http.HttpServletResponse response) {
        try {
            Tagihan tagihan = tagihanService.getAllTagihan().stream()
                    .filter(t -> t.getIdTagihan().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Tagihan tidak ditemukan"));

            response.setContentType("application/pdf");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=Invoice_Kost_#INV-" + tagihan.getIdTagihan() + ".pdf";
            response.setHeader(headerKey, headerValue);

            com.tup.kost_management.utils.InvoicePdfGenerator.generate(tagihan, response);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(500, "Gagal mengunduh cetak dokumen invoice: " + e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}