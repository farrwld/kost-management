package com.tup.kost_management.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

    @GetMapping("/")
    public String landingPage(HttpSession session, Model model) {
        // Jika user ternyata sudah login dan iseng membuka landing page, kita kirim data session agar navigasi navbar bisa menyesuaikan secara cerdas
        String role = (String) session.getAttribute("userRole");
        if (role != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("roleUser", role);
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        return "landing-page"; // Membuka landing.html
    }

    @GetMapping("/dashboard")
    public String index(HttpSession session, Model model, @ModelAttribute("errorAkses") String errorAkses) {
        String role = (String) session.getAttribute("userRole");
        if (role == null) {
            return "redirect:/login"; // Tendang ke login jika belum autentikasi
        }

        model.addAttribute("namaUser", session.getAttribute("userName"));
        model.addAttribute("roleUser", role);

        // Kirim string kosong jika tidak ada errorAkses agar Thymeleaf tidak mencetak teks aneh
        if (errorAkses != null && !errorAkses.isEmpty()) {
            model.addAttribute("errorAkses", errorAkses);
        }

        return "index"; // Membuka index.html (Dashboard utama Anda)
    }
}