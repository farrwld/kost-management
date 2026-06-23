package com.tup.kost_management.controller;

import com.tup.kost_management.entity.Kamar;
import com.tup.kost_management.entity.KontrakSewa;
import com.tup.kost_management.entity.Penghuni;
import com.tup.kost_management.service.KamarService;
import com.tup.kost_management.service.KontrakSewaService;
import com.tup.kost_management.service.PenghuniService; // Tambahkan import ini
import com.tup.kost_management.repository.KontrakSewaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/kontrak")
public class KontrakSewaController {

    @Autowired
    private KontrakSewaService kontrakSewaService;

    @Autowired
    private PenghuniService penghuniService; // Ganti UserRepository dengan PenghuniService

    @Autowired
    private KamarService kamarService;

    @Autowired
    private KontrakSewaRepository kontrakSewaRepository;

    @GetMapping
    public String tampilkanHalamanKontrak(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (role == null)
            return "redirect:/login";

        if (!"ADMIN".equals(role)) {
            session.setAttribute("errorAkses", "Maaf, halaman Kontrak Sewa hanya boleh diakses oleh Admin!");
            return "redirect:/";
        }

        model.addAttribute("daftarKontrak", kontrakSewaService.getAllKontrak());
        
        model.addAttribute("daftarPenghuni", penghuniService.getPenghuniAktif());
        
        model.addAttribute("daftarKamar", kamarService.getKamarTersedia());
        
        model.addAttribute("roleUser", role);
        return "kontrak-view";
    }

    @PostMapping("/tambah")
    public String tambahKontrakWeb(
            @RequestParam Long idPenghuni,
            @RequestParam Long idKamar,
            @RequestParam String tglMulai,
            @RequestParam Double deposit) {

        KontrakSewa kontrak = new KontrakSewa();
        LocalDate mulai = LocalDate.parse(tglMulai);

        kontrak.setTglMulai(mulai);
        kontrak.setTglSelesai(mulai.plusMonths(1));
        kontrak.setDeposit(deposit);
        kontrak.setStatusKontrak("AKTIF");

        Penghuni p = new Penghuni();
        p.setIdUser(idPenghuni);
        kontrak.setPenghuni(p);

        Kamar k = kamarService.getAllKamar().stream()
                .filter(room -> room.getIdKamar().equals(idKamar))
                .findFirst().orElseThrow();
        k.setStatus("TERISI");
        kamarService.saveKamar(k);

        kontrak.setKamar(k);

        try {
            kontrakSewaService.buatKontrakBaru(kontrak);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/kontrak";
    }

    @PostMapping("/akhiri/{id}")
    public String akhiriKontrakWeb(@PathVariable Long id) {
        KontrakSewa kontrak = kontrakSewaRepository.findById(id).orElseThrow();
        kontrak.setStatusKontrak("BERAKHIR");

        if (kontrak.getKamar() != null) {
            Kamar kamarAsli = kontrak.getKamar();
            kamarAsli.setStatus("TERSEDIA");
            kamarService.saveKamar(kamarAsli);
        }

        kontrakSewaRepository.save(kontrak);
        return "redirect:/kontrak";
    }
}