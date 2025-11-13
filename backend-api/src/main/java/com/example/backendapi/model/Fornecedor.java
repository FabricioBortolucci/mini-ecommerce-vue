package com.example.backendapi.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fornecedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "forn_nome")
    private String nome;

    @Column(name = "forn_telefone")
    private String telefone;

    @Column(name = "forn_email")
    private String email;

    @Column(name = "forn_cnpj")
    private String cnpj;

    @Column(name = "profile_image_url")
    private String profileImageUrl;
}
