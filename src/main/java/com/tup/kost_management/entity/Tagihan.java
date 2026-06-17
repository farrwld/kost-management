package com.tup.kost_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "tagihan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tagihan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTagihan;

    @NotNull(message = "Jumlah tagihan tidak boleh kosong")
    @Min(value = 0, message = "Jumlah tagihan tidak boleh bernilai negatif")
    @Column(name = "jumlah_tagihan", nullable = false)
    private Double jumlahTagihan;

    @NotBlank(message = "Status bayar tidak boleh kosong")
    @Column(name = "status_bayar", nullable = false)
    private String statusBayar; // Misal: "BELUM_BAYAR", "LUNAS"

    @NotNull(message = "Tanggal jatuh tempo tidak boleh kosong")
    @Column(name = "jatuh_tempo", nullable = false)
    private LocalDate jatuhTempo;

    @NotBlank(message = "Periode tagihan tidak boleh kosong")
    @Column(nullable = false)
    private String periode; // Misal: "Januari 2026", "Februari 2026"

    // Relasi ke Penghuni (Many tagihan to One penghuni)
    @ManyToOne
    @JoinColumn(name = "id_penghuni", nullable = false)
    private Penghuni penghuni;
}
