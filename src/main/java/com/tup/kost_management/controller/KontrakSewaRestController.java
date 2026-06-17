package com.tup.kost_management.controller;

import com.tup.kost_management.entity.KontrakSewa;
import com.tup.kost_management.service.KontrakSewaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kontrak")
public class KontrakSewaRestController {

    @Autowired
    private KontrakSewaService kontrakSewaService;

    @GetMapping
    public List<KontrakSewa> getAllKontrakApi() {
        return kontrakSewaService.getAllKontrak();
    }

    @PostMapping
    public ResponseEntity<KontrakSewa> buatKontrakApi(@Valid @RequestBody KontrakSewa kontrak) {
        KontrakSewa kontrakBaru = kontrakSewaService.buatKontrakBaru(kontrak);
        return ResponseEntity.ok(kontrakBaru);
    }
}
