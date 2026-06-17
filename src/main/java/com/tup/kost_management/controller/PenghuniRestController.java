package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Penghuni;
import com.tup.kost_management.service.PenghuniService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/penghuni")
public class PenghuniRestController {

    @Autowired
    private PenghuniService penghuniService;

    @GetMapping
    public List<Penghuni> getAllPenghuniApi() {
        return penghuniService.getAllPenghuni();
    }

    @PostMapping
    public ResponseEntity<Penghuni> registrasiPenghuniApi(@Valid @RequestBody Penghuni penghuni) {
        Penghuni penghuniBaru = penghuniService.registerPenghuni(penghuni);
        return ResponseEntity.ok(penghuniBaru);
    }
}