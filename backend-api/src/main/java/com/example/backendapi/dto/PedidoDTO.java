package com.example.backendapi.dto;


import com.example.backendapi.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDTO(
        Long id,
        LocalDateTime dataPedido,
        BigDecimal valorTotal,
        StatusPedido status,
        List<PedidoItemDTO> itens
) {
}
