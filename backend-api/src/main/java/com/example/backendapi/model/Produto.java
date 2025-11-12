package com.example.backendapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prod_nome")
    private String nome;

    @Column(name = "prod_desc")
    private String descricao;

    @Column(name = "prod_preco")
    private BigDecimal preco;

    @Column(name = "prod_image")
    private String imageUrl;
}
