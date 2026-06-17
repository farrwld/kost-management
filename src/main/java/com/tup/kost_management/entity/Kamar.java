package com.tup.kost_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "kamar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kamar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKamar;

    @NotBlank(message = "Nomor kamar tidak boleh kosong")
    @Column(name = "no_kamar", unique = true, nullable = false)
    private String noKamar;

    @NotNull(message = "Harga sewa tidak boleh kosong")
    @Min(value = 0, message = "Harga sewa tidak boleh bernilai negatif")
    @Column(name = "harga_sewa", nullable = false)
    private Double hargaSewa;

    @NotBlank(message = "Status kamar harus ditentukan")
    @Column(nullable = false)
    private String status; // Misal: "TERSEDIA", "TERISI", "MAINTENANCE"

    @Column(columnDefinition = "TEXT")
    private String fasilitas;
}