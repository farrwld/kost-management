package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Kamar;
import com.tup.kost_management.service.KamarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kamar")
public class KamarRestController {

    @Autowired
    private KamarService kamarService;

    // GET: http://localhost:8080/api/kamar
    @GetMapping
    public List<Kamar> getAllKamarApi() {
        return kamarService.getAllKamar();
    }

    // POST: http://localhost:8080/api/kamar
    @PostMapping
    public ResponseEntity<Kamar> tambahKamarApi(@Valid @RequestBody Kamar kamar) {
        Kamar kamarBaru = kamarService.saveKamar(kamar);
        return ResponseEntity.ok(kamarBaru);
    }
}
