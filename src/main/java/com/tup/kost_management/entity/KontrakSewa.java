package com.tup.kost_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "kontrak_sewa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KontrakSewa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKontrak;

    @NotNull(message = "Tanggal mulai tidak boleh kosong")
    @Column(name = "tgl_mulai", nullable = false)
    private LocalDate tglMulai;

    @NotNull(message = "Tanggal selesai tidak boleh kosong")
    @Column(name = "tgl_selesai", nullable = false)
    private LocalDate tglSelesai;

    @Min(value = 0, message = "Deposit tidak boleh bernilai negatif")
    private Double deposit;

    // Relasi ke Penghuni (Banyak kontrak bisa dibuat untuk penghuni yang sama seiring waktu)
    @ManyToOne
    @JoinColumn(name = "id_penghuni", nullable = false)
    private Penghuni penghuni;

    // Relasi ke Kamar
    @ManyToOne
    @JoinColumn(name = "id_kamar", nullable = false)
    private Kamar kamar;

    @Column(name = "status_kontrak", nullable = false)
    private String statusKontrak; // Nilai: "AKTIF" atau "BERAKHIR"
}