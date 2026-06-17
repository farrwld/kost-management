package com.tup.kost_management.controller;

import com.tup.kost_management.service.KamarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kamar")
public class KamarController {

    @Autowired
    private KamarService kamarService;

    // Browser: http://localhost:8080/kamar
    @GetMapping
    public String tampilkanHalamanKamar(Model model) {
        // Mengirim data list kamar dari database ke file HTML dengan nama variabel "daftarKamar"
        model.addAttribute("daftarKamar", kamarService.getAllKamar());
        return "kamar-view"; // Ini akan mencari file src/main/resources/templates/kamar-view.html
    }
}
