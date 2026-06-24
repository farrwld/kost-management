package com.tup.kost_management.utils;

import com.tup.kost_management.entity.Tagihan;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class InvoicePdfGenerator {

    public static void generate(Tagihan tagihan, HttpServletResponse response) throws DocumentException, IOException {
        // Ukuran A5 dengan margin yang proporsional (Kiri, Kanan, Atas, Bawah)
        Document document = new Document(PageSize.A5, 30, 30, 30, 30); 
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // -------------------------------------------------------------
        // PALET WARNA (Modern & Professional Palette)
        // -------------------------------------------------------------
        Color primaryColor = new Color(28, 40, 51);     // Navy / Charcoal Tua
        Color secondaryColor = new Color(110, 114, 117); // Abu-abu Elegan
        Color lightBgColor = new Color(245, 247, 248);   // Kertas Putih Abu Ringan
        Color successColor = new Color(40, 167, 69);     // Hijau Sukses Lunas

        // -------------------------------------------------------------
        // PENGATURAN FONT
        // -------------------------------------------------------------
        Font fontBrand = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, secondaryColor);
        Font fontJudul = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, primaryColor);
        Font fontSub = FontFactory.getFont(FontFactory.HELVETICA, 9, secondaryColor);
        Font fontTableHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
        Font fontTableData = FontFactory.getFont(FontFactory.HELVETICA, 10, primaryColor);
        Font fontTableDataBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, primaryColor);
        Font fontStatusLunas = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, successColor);

        // -------------------------------------------------------------
        // HEADER SECTION (Layout Atas)
        // -------------------------------------------------------------
        Paragraph brand = new Paragraph("KOST MANAGEMENT SYSTEM", fontBrand);
        brand.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(brand);

        Paragraph judul = new Paragraph("INVOICE PEMBAYARAN", fontJudul);
        judul.setSpacingBefore(5f);
        document.add(judul);

        Paragraph subJudul = new Paragraph("Telkom University Purwokerto  •  Kelompok 06 PBO", fontSub);
        subJudul.setSpacingAfter(15f);
        document.add(subJudul);

        PdfPTable lineTable = new PdfPTable(1);
        lineTable.setWidthPercentage(100);
        PdfPCell lineCell = new PdfPCell();
        lineCell.setBorder(Rectangle.BOTTOM);
        lineCell.setBorderWidth(1.5f);
        lineCell.setBorderColor(primaryColor);
        lineTable.addCell(lineCell);
        document.add(lineTable);

        // -------------------------------------------------------------
        // METADATA SECTION (FIXED: Jalur Pemanggilan Menggunakan KontrakSewa)
        // -------------------------------------------------------------
        PdfPTable metaTable = new PdfPTable(2);
        metaTable.setWidthPercentage(100);
        metaTable.setSpacingBefore(12f);
        metaTable.setSpacingAfter(15f);

        // Kolom Kiri: Detail Penghuni & Informasi Kamar Otomatis
        String namaPenghuni = "-";
        String nomorKamar = "-";
        
        if (tagihan.getKontrakSewa() != null) {
            if (tagihan.getKontrakSewa().getPenghuni() != null) {
                namaPenghuni = tagihan.getKontrakSewa().getPenghuni().getUsername();
            }
            if (tagihan.getKontrakSewa().getKamar() != null) {
                nomorKamar = tagihan.getKontrakSewa().getKamar().getNoKamar();
            }
        }
        
        Paragraph pLeft = new Paragraph(
                "Ditagihkan Kepada:\n" +
                "Nama    : " + namaPenghuni + "\n" +
                "Kamar   : " + nomorKamar + "\n" +
                "Status  : Aktif", fontTableData);
        pLeft.setLeading(14f); 
        
        PdfPCell metaLeft = new PdfPCell();
        metaLeft.addElement(pLeft); 
        metaLeft.setBorder(Rectangle.NO_BORDER);
        metaTable.addCell(metaLeft);

        // Kolom Kanan: Detail Waktu Tagihan
        String tglJatuhTempo = "-";
        if (tagihan.getJatuhTempo() != null) {
            tglJatuhTempo = tagihan.getJatuhTempo().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        }
        
        Paragraph pRight = new Paragraph(
                "No. Invoice : #INV-" + tagihan.getIdTagihan() + "\n" +
                "Periode     : " + tagihan.getPeriode() + "\n" +
                "Jatuh Tempo : " + tglJatuhTempo, fontTableData);
        pRight.setLeading(14f); 
        pRight.setAlignment(Element.ALIGN_RIGHT);
        
        PdfPCell metaRight = new PdfPCell();
        metaRight.addElement(pRight); 
        metaRight.setBorder(Rectangle.NO_BORDER);
        metaTable.addCell(metaRight);

        document.add(metaTable);

        // -------------------------------------------------------------
        // RENDER TABEL UTAMA (Detail Finansial)
        // -------------------------------------------------------------
        float[] columnWidths = {6f, 4f};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);

        addHeaderCell(table, "DESKRIPSI TAGIHAN", fontTableHeader, primaryColor);
        addHeaderCell(table, "TOTAL NOMINAL", fontTableHeader, primaryColor);

        // Baris 1: Detail Kamar & Bulan
        addDataCell(table, "Biaya Sewa Kamar " + nomorKamar + " (Periode " + tagihan.getPeriode() + ")", fontTableData, false, lightBgColor);
        addDataCell(table, "Rp " + String.format("%,.0f", tagihan.getJumlahTagihan()), fontTableData, true, lightBgColor);

        // Baris 2: Informasi Status Administrasi
        addDataCell(table, "Status Kelayakan Administrasi", fontTableData, false, Color.WHITE);
        if ("LUNAS".equalsIgnoreCase(tagihan.getStatusBayar())) {
            addDataCell(table, "LUNAS (Terverifikasi)", fontStatusLunas, true, Color.WHITE);
        } else {
            Font fontBelumLunas = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.RED);
            addDataCell(table, "BELUM LUNAS", fontBelumLunas, true, Color.WHITE);
        }

        // Baris Terakhir: Grand Total Accent Row
        Color totalRowColor = new Color(230, 235, 240);
        addTotalCell(table, "TOTAL YANG DIBAYARKAN", fontTableDataBold, false, totalRowColor);
        addTotalCell(table, "Rp " + String.format("%,.0f", tagihan.getJumlahTagihan()), fontTableDataBold, true, totalRowColor);

        document.add(table);

        // -------------------------------------------------------------
        // FOOTER SECTION
        // -------------------------------------------------------------
        Paragraph footerText = new Paragraph("Kuitansi ini sah dan diterbitkan secara otomatis oleh sistem keuangan internal Kost Management.", fontSub);
        footerText.setAlignment(Paragraph.ALIGN_CENTER);
        footerText.setSpacingBefore(25f);
        document.add(footerText);

        Paragraph terimaKasih = new Paragraph("Terima kasih telah melakukan pembayaran tepat waktu!", fontTableDataBold);
        terimaKasih.setAlignment(Paragraph.ALIGN_CENTER);
        terimaKasih.setSpacingBefore(5f);
        document.add(terimaKasih);

        document.close();
    }

    private static void addHeaderCell(PdfPTable table, String text, Font font, Color bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingTop(8);
        cell.setPaddingBottom(8);
        cell.setPaddingLeft(10);
        cell.setPaddingRight(10);
        if (text.contains("TOTAL")) {
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        }
        table.addCell(cell);
    }

    private static void addDataCell(PdfPTable table, String text, Font font, boolean isRightAlign, Color bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidth(0.5f);
        cell.setBorderColor(new Color(220, 224, 227)); 
        cell.setPaddingTop(10);
        cell.setPaddingBottom(10);
        cell.setPaddingLeft(10);
        cell.setPaddingRight(10);
        if (isRightAlign) {
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        }
        table.addCell(cell);
    }

    private static void addTotalCell(PdfPTable table, String text, Font font, boolean isRightAlign, Color bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setBorder(Rectangle.BOX);
        cell.setBorderWidth(1f);
        cell.setBorderColor(new Color(180, 190, 200));
        cell.setPaddingTop(10);
        cell.setPaddingBottom(10);
        cell.setPaddingLeft(10);
        cell.setPaddingRight(10);
        if (isRightAlign) {
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        }
        table.addCell(cell);
    }
}