package com.tup.kost_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "penghuni")
@PrimaryKeyJoinColumn(name = "id_user") // Menghubungkan ID Penghuni ke ID User
@Data
@EqualsAndHashCode(callSuper = true)
public class Penghuni extends User {

    // Kamu bisa menambahkan atribut spesifik penghuni di sini nantinya,
    // seperti noHp atau statusAktif jika diperlukan.
}