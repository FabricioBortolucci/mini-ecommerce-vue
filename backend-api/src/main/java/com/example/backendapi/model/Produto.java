package com.example.backendapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Produto implements Serializable {

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

    @ManyToOne
    private Fornecedor fornecedor;
}
