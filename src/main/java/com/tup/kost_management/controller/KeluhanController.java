package com.tup.kost_management.controller;

import com.tup.kost_management.service.KeluhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/keluhan")
public class KeluhanController {

    @Autowired
    private KeluhanService keluhanService;

    @GetMapping
    public String tampilkanHalamanKeluhan(Model model) {
        model.addAttribute("daftarKeluhan", keluhanService.getAllKeluhan());
        return "keluhan-view";
    }
}