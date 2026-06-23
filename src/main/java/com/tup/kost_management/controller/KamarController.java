package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Kamar;
import com.tup.kost_management.service.KamarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/kamar")
public class KamarController {

    @Autowired
    private KamarService kamarService;

    @GetMapping
    public String tampilkanHalamanKamar(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (role == null) return "redirect:/login";

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
        kamar.setStatus("TERSEDIA");
        
        kamarService.saveKamar(kamar);
        return "redirect:/kamar";
    }

    @PostMapping("/nonaktifkan/{id}")
    public String nonaktifkanKamarWeb(@PathVariable Long id) {
        kamarService.nonaktifkanKamar(id);
        return "redirect:/kamar";
    }

    @PostMapping("/aktifkan/{id}")
    public String aktifkanKamarWeb(@PathVariable Long id) {
        kamarService.aktifkanKamar(id);
        return "redirect:/kamar";
    }
}