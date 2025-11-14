package com.example.backendapi.dto;


import java.math.BigDecimal;

public record PedidoItemDTO(
        Long id,
        Integer quantidade,
        BigDecimal precoUnitario,
        ProdutoDTO produto
) {
}
