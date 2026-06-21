package com.tup.kost_management.controller;

import com.tup.kost_management.service.PenghuniService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/penghuni")
public class PenghuniController {

    @Autowired
    private PenghuniService penghuniService;

    @GetMapping
    public String tampilkanHalamanPenghuni(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (role == null)
            return "redirect:/login";

        if (!"ADMIN".equals(role)) {
            redirectAttributes.addFlashAttribute("errorAkses",
                    "Maaf, data seluruh penghuni hanya boleh diakses oleh Admin!");
            return "redirect:/";
        }

        model.addAttribute("daftarPenghuni", penghuniService.getAllPenghuni());
        return "penghuni-view";
    }
}
