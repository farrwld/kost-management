package com.tup.kost_management.utils;

import com.tup.kost_management.entity.Tagihan;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;

public class InvoicePdfGenerator {

    public static void generate(Tagihan tagihan, HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A5); // Ukuran kertas A5 cocok untuk nota/invoice
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // Pengaturan Font
        Font fontJudul = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
        Font fontSub = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY);
        Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.BLACK);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 11, Color.BLACK);

        // Header Invoice
        Paragraph judul = new Paragraph("INVOICE PEMBAYARAN KOST", fontJudul);
        judul.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(judul);

        Paragraph subJudul = new Paragraph("Telkom University Purwokerto - Tubes Kelompok 06\n\n", fontSub);
        subJudul.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(subJudul);

        // Garis Pembatas
        Paragraph garis = new Paragraph("========================================", fontSub);
        garis.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(garis);

        // Detail Konten Nota (Tabel)
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15f);
        table.setSpacingAfter(15f);

        // Helper untuk tambah cell
        addTableCell(table, "ID Tagihan", fontBold);
        addTableCell(table, String.valueOf(tagihan.getIdTagihan()), fontNormal);

        addTableCell(table, "Bulan Tagihan", fontBold);
        addTableCell(table, tagihan.getPeriode(), fontNormal);

        addTableCell(table, "Total Nominal", fontBold);
        addTableCell(table, "Rp " + String.format("%,.0f", tagihan.getJumlahTagihan()), fontNormal);

        addTableCell(table, "Status Pembayaran", fontBold);
        addTableCell(table, tagihan.getStatusBayar(), fontNormal);

        document.add(table);
        document.add(garis);

        // Catatan Footer
        Paragraph footer = new Paragraph("\nTerima kasih telah melakukan pembayaran tepat waktu!", fontSub);
        footer.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(footer);

        document.close();
    }

    private static void addTableCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(6);
        table.addCell(cell);
    }
}