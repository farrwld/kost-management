package com.tup.kost_management.service;

import com.tup.kost_management.entity.Tagihan;
import com.tup.kost_management.entity.KontrakSewa;
import com.tup.kost_management.entity.Penghuni;
import com.tup.kost_management.repository.TagihanRepository;
import com.tup.kost_management.repository.KontrakSewaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class TagihanService {

    @Autowired
    private TagihanRepository tagihanRepository;

    @Autowired
    private KontrakSewaRepository kontrakSewaRepository;

    public List<Tagihan> getAllTagihan() {
        return tagihanRepository.findAll();
    }

    // Admin: Menerbitkan tagihan cerdas terintegrasi Logika A
    public Tagihan buatTagihanOtomatis(Long idKontrak, String periode, String jatuhTempoStr) {
        KontrakSewa kontrak = kontrakSewaRepository.findById(idKontrak)
                .orElseThrow(() -> new RuntimeException("Kontrak dengan ID " + idKontrak + " tidak ditemukan"));

        // Ambil nominal dasar dari spesifikasi kamar dalam kontrak
        Double hargaDasarKamar = kontrak.getKamar().getHargaSewa();
        Double jaminanDeposit = kontrak.getDeposit();

        // Deteksi berkala: Apakah sudah ada tagihan sebelum ini untuk kontrak yang bersangkutan?
        boolean apakahTagihanPertama = !tagihanRepository.existsByKontrakSewa(kontrak);
        
        Double kalkulasiAkhirTagihan = hargaDasarKamar;

        if (apakahTagihanPertama) {
            // Logika A: Potong dengan deposit di bulan pertama sewa
            kalkulasiAkhirTagihan = hargaDasarKamar - jaminanDeposit;
            if (kalkulasiAkhirTagihan < 0) kalkulasiAkhirTagihan = 0.0;
        }

        Tagihan tagihanNew = new Tagihan();
        tagihanNew.setKontrakSewa(kontrak);
        tagihanNew.setPeriode(periode);
        tagihanNew.setJumlahTagihan(kalkulasiAkhirTagihan);
        tagihanNew.setJatuhTempo(java.time.LocalDate.parse(jatuhTempoStr));
        tagihanNew.setStatusBayar("BELUM_BAYAR");

        return tagihanRepository.save(tagihanNew);
    }

    public List<Tagihan> getTagihanByPenghuni(Long idUser) {
        return tagihanRepository.findByKontrakSewaPenghuniIdUser(idUser);
    }

    public Tagihan bayarTagihan(Long idTagihan) {
        Tagihan tagihan = tagihanRepository.findById(idTagihan)
                .orElseThrow(() -> new RuntimeException("Tagihan tidak ditemukan dengan ID: " + idTagihan));
        
        tagihan.setStatusBayar("LUNAS");
        
        // Pembersihan objek melingkar untuk pipeline Invoice PDF Generator aman
        Long idPenghuniAsli = tagihan.getKontrakSewa().getPenghuni().getIdUser();
        Penghuni penghuniDibersihkan = new Penghuni();
        penghuniDibersihkan.setIdUser(idPenghuniAsli);
        tagihan.getKontrakSewa().setPenghuni(penghuniDibersihkan);

        return tagihanRepository.save(tagihan);
    }
}