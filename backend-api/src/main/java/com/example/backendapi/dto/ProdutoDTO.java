package com.example.backendapi.dto;

import java.math.BigDecimal;

public record ProdutoDTO(
        Long id,
        String nome,
        String imageUrl,
        BigDecimal preco,
        Long fornecedorId,
        String descricao
) {
}
