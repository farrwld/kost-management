package com.tup.kost_management.controller;

import com.tup.kost_management.service.KontrakSewaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kontrak")
public class KontrakSewaController {

    @Autowired
    private KontrakSewaService kontrakSewaService;

    @GetMapping
    public String tampilkanHalamanKontrak(Model model) {
        model.addAttribute("daftarKontrak", kontrakSewaService.getAllKontrak());
        return "kontrak-view";
    }
}
