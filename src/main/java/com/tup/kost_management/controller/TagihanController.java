package com.tup.kost_management.controller;

import com.tup.kost_management.service.TagihanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tagihan")
public class TagihanController {

    @Autowired
    private TagihanService tagihanService;

    @GetMapping
    public String tampilkanHalamanTagihan(Model model) {
        model.addAttribute("daftarTagihan", tagihanService.getAllTagihan());
        return "tagihan-view";
    }
}
