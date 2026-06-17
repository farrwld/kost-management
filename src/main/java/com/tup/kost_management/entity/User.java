package com.tup.kost_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Username tidak boleh kosong")
    @Column(unique = true, nullable = false)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password tidak boleh kosong")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Role harus ditentukan")
    @Column(nullable = false)
    private String role; // Nilainya bisa "ADMIN" atau "PENGHUNI"

}