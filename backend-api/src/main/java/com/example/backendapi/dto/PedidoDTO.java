package com.example.backendapi.dto;


import java.util.List;

public record PedidoDTO(
        List<PedidoItemDTO> itens
) {
}
