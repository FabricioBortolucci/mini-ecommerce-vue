package com.example.backendapi.dto;

import java.util.List;

public record PedidoRequestDTO(List<ItemPedidoRequestDTO> itens) {
}
