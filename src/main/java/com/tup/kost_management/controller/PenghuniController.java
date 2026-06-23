package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Penghuni;
import com.tup.kost_management.service.PenghuniService;
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
@RequestMapping("/penghuni")
public class PenghuniController {

    @Autowired
    private PenghuniService penghuniService;

    @GetMapping
    public String tampilkanHalamanPenghuni(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (role == null) return "redirect:/login";

        if (!"ADMIN".equals(role)) {
            session.setAttribute("errorAkses", "Maaf, data seluruh penghuni hanya boleh diakses oleh Admin!");
            return "redirect:/";
        }

        // Ambil data lewat service agar status keaktifan terangkut dengan benar
        model.addAttribute("daftarPenghuni", penghuniService.getAllPenghuni());
        return "penghuni-view";
    }

    @PostMapping("/tambah")
    public String tambahPenghuniWeb(
            @RequestParam String username,
            @RequestParam String password) {

        Penghuni penghuni = new Penghuni();
        penghuni.setUsername(username);
        penghuni.setPassword(password);

        penghuniService.registerPenghuni(penghuni);
        return "redirect:/penghuni";
    }

    // Endpoint Aksi Nonaktifkan Akun
    @PostMapping("/nonaktifkan/{id}")
    public String nonaktifkanPenghuniWeb(@PathVariable Long id) {
        penghuniService.nonaktifkanPenghuni(id);
        return "redirect:/penghuni";
    }

    // Endpoint Aksi Aktifkan Kembali Akun
    @PostMapping("/aktifkan/{id}")
    public String aktifkanPenghuniWeb(@PathVariable Long id) {
        penghuniService.aktifkanPenghuni(id);
        return "redirect:/penghuni";
    }
}