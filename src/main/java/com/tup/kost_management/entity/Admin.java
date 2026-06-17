package com.tup.kost_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "id_user") // Menghubungkan ID Admin ke ID User
@Data
@EqualsAndHashCode(callSuper = true) // Agar Lombok menyertakan properti dari Parent di method equals/hashCode
public class Admin extends User {
    
    // Sesuai class diagram, admin tidak memiliki atribut spesifik tambahan,
    // melainkan hanya memiliki operasi/behavior khusus seperti manageKamar(), dll.
    // Atribut tambahan bisa ditambahkan di sini jika nanti diperlukan (misal: namaAdmin).
}