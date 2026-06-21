package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Keluhan;
import com.tup.kost_management.service.KeluhanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keluhan")
public class KeluhanRestController {

    @Autowired
    private KeluhanService keluhanService;

    // GET: http://localhost:8080/api/keluhan (Admin melihat semua keluhan)
    @GetMapping
    public List<Keluhan> getAllKeluhanApi() {
        return keluhanService.getAllKeluhan();
    }

    // POST: http://localhost:8080/api/keluhan (Penghuni menginput keluhan baru)
    @PostMapping
    public ResponseEntity<Keluhan> inputKeluhanApi(@RequestBody Keluhan keluhan) { // @Valid dihapus
        Keluhan keluhanBaru = keluhanService.inputKeluhan(keluhan);
        return ResponseEntity.ok(keluhanBaru);
    }

    // PUT: http://localhost:8080/api/keluhan/status/1?statusBaru=DIPROSES (Admin
    // update status)
    @PutMapping("/status/{id}")
    public ResponseEntity<Keluhan> updateStatusApi(@PathVariable Long id, @RequestParam String statusBaru) {
        Keluhan keluhanUpdated = keluhanService.updateStatusMaintenance(id, statusBaru);
        return ResponseEntity.ok(keluhanUpdated);
    }
}
