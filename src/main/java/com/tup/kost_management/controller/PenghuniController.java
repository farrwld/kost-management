package com.tup.kost_management.controller;

import com.tup.kost_management.service.PenghuniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/penghuni")
public class PenghuniController {

    @Autowired
    private PenghuniService penghuniService;

    @GetMapping
    public String tampilkanHalamanPenghuni(Model model) {
        model.addAttribute("daftarPenghuni", penghuniService.getAllPenghuni());
        return "penghuni-view";
    }
}
