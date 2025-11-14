package com.example.backendapi.services;

import com.example.backendapi.repositories.PedidoItemRepository;
import org.springframework.stereotype.Service;

@Service
public class PedidoItemService {

    private final PedidoItemRepository pedidoItemRepository;


    public PedidoItemService(PedidoItemRepository pedidoItemRepository) {
        this.pedidoItemRepository = pedidoItemRepository;
    }
}
