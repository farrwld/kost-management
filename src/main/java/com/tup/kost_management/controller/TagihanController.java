package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Tagihan;
import com.tup.kost_management.entity.User;
import com.tup.kost_management.repository.UserRepository;
import com.tup.kost_management.service.TagihanService;
import com.tup.kost_management.utils.InvoicePdfGenerator;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
import java.util.List;

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
            List<Tagihan> semuaTagihan = tagihanService.getAllTagihan();
            semuaTagihan.removeIf(java.util.Objects::isNull); // Buang elemen null jika ada
            model.addAttribute("daftarTagihan", semuaTagihan);
        } else {
            // Jika PENGHUNI: Cari ID user-nya terlebih dahulu dari database berdasarkan
            // username session
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

    @GetMapping("/cetak/{id}")
    public void cetakInvoicePdf(@PathVariable Long id, HttpServletResponse response) {
        try {
            // 1. Ambil data tagihan spesifik dari database via JPA (Cari object tagihan
            // asli)
            // Disini kita asumsikan tagihanService punya method getTagihanById
            Tagihan tagihan = tagihanService.getAllTagihan().stream()
                    .filter(t -> t.getIdTagihan().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Tagihan tidak ditemukan"));

            // 2. Set metadata response browser agar otomatis mengenali file PDF unduhan
            response.setContentType("application/pdf");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=Invoice_Kost_ID_" + id + ".pdf";
            response.setHeader(headerKey, headerValue);

            // 3. Eksekusi pembuatan PDF-nya
            InvoicePdfGenerator.generate(tagihan, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}