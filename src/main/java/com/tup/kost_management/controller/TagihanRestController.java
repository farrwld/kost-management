package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Tagihan;
import com.tup.kost_management.service.TagihanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
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
    // Menggunakan DTO agar selaras dengan skema database kontrak sewa + Logika A otomatis
    @PostMapping
    public ResponseEntity<Tagihan> buatTagihanApi(@Valid @RequestBody TagihanRequestDTO request) {
        Tagihan tagihanBaru = tagihanService.buatTagihanOtomatis(
                request.getIdKontrak(),
                request.getPeriode(),
                request.getJatuhTempo()
        );
        return ResponseEntity.ok(tagihanBaru);
    }

    // PUT: http://localhost:8080/api/tagihan/bayar/1
    @PutMapping("/bayar/{id}")
    public ResponseEntity<Tagihan> bayarTagihanApi(@PathVariable Long id) {
        Tagihan tagihanLunas = tagihanService.bayarTagihan(id);
        return ResponseEntity.ok(tagihanLunas);
    }
}

// DTO Helper untuk memetakan JSON request body dari Postman
@Data
class TagihanRequestDTO {
    @NotNull(message = "ID Kontrak tidak boleh kosong")
    private Long idKontrak;

    @NotBlank(message = "Periode tidak boleh kosong")
    private String periode;

    @NotBlank(message = "Tanggal jatuh tempo tidak boleh kosong")
    private String jatuhTempo; // Format: "YYYY-MM-DD"
}