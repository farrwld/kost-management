package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Kamar;
import com.tup.kost_management.service.KamarService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/kamar")
public class KamarController {

    @Autowired
    private KamarService kamarService;

    // Browser: http://localhost:8080/kamar
    // Sembunyikan isi method asli di dalam pengecekan ini:
    @GetMapping
    public String tampilkanHalamanKamar(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (role == null)
            return "redirect:/login";

        if (!"ADMIN".equals(role)) {
            redirectAttributes.addFlashAttribute("errorAkses",
                    "Maaf, halaman Manajemen Kamar hanya boleh diakses oleh Admin!");
            return "redirect:/"; // Tendang kembali ke dashboard jika dia cuma PENGHUNI
        }

        model.addAttribute("daftarKamar", kamarService.getAllKamar());
        return "kamar-view";
    }

    @PostMapping("/tambah")
    public String tambahKamarWeb(
            @RequestParam String nomorKamar, 
            @RequestParam Double harga, 
            @RequestParam String fasilitasKamar) {
        
        Kamar kamar = new Kamar();
        kamar.setNoKamar(nomorKamar);
        kamar.setFasilitas(fasilitasKamar);
        kamar.setHargaSewa(harga);
        kamar.setStatus("TERSEDIA"); // Default kamar baru adalah kosong
        
        kamarService.saveKamar(kamar);
        return "redirect:/kamar";
    }
}
