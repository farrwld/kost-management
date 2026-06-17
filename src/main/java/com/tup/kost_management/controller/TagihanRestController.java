package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Tagihan;
import com.tup.kost_management.service.TagihanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tagihan")
public class TagihanRestController {

    @Autowired
    private TagihanService tagihanService;

    // GET: http://localhost:8080/api/tagihan
    @GetMapping
    public List<Tagihan> getAllTagihanApi() {
        return tagihanService.getAllTagihan();
    }

    // POST: http://localhost:8080/api/tagihan
    @PostMapping
    public ResponseEntity<Tagihan> buatTagihanApi(@Valid @RequestBody Tagihan tagihan) {
        Tagihan tagihanBaru = tagihanService.buatTagihan(tagihan);
        return ResponseEntity.ok(tagihanBaru);
    }

    // PUT: http://localhost:8080/api/tagihan/bayar/1 (Simulasi Bayar Tagihan)
    @PutMapping("/bayar/{id}")
    public ResponseEntity<Tagihan> bayarTagihanApi(@PathVariable Long id) {
        Tagihan tagihanLunas = tagihanService.bayarTagihan(id);
        return ResponseEntity.ok(tagihanLunas);
    }
}
