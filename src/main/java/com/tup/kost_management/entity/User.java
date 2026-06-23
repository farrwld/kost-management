package com.tup.kost_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED) // Strategi Inheritance JPA
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotBlank(message = "Username tidak boleh kosong")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password tidak boleh kosong")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Role harus ditentukan")
    @Column(nullable = false)
    private String role; // "ADMIN" atau "PENGHUNI"

    // Tambahkan properti keaktifan di tingkat User utama
    @Column(name = "is_aktif", nullable = false)
    private boolean isAktif = true; 
}