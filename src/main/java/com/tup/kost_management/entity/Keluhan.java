package com.tup.kost_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "keluhan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Keluhan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKeluhan;

    @NotNull(message = "Tanggal keluhan tidak boleh kosong")
    @Column(name = "tgl_keluhan", nullable = false)
    private LocalDate tglKeluhan;

    @NotBlank(message = "Deskripsi keluhan tidak boleh kosong")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String deskripsi;

    @NotBlank(message = "Status perbaikan tidak boleh kosong")
    @Column(name = "status_perbaikan", nullable = false)
    private String statusPerbaikan; // Misal: "PENDING", "DIPROSES", "SELESAI"

    // Relasi ke Penghuni (Many keluhan to One penghuni)
    @ManyToOne
    @JoinColumn(name = "id_penghuni", nullable = false)
    private Penghuni penghuni;
}
