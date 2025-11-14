package com.example.backendapi.controller;

import com.example.backendapi.dto.PedidoDTO;
import com.example.backendapi.dto.PedidoRequestDTO;
import com.example.backendapi.model.Pedido;
import com.example.backendapi.model.Produto;
import com.example.backendapi.services.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoRequestDTO request,
                                              Authentication authentication) {
        String username = authentication.getName();

        Pedido pedidoCriado = pedidoService.criarPedido(request, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
    }

    @GetMapping("/meus-pedidos")
    public ResponseEntity<List<PedidoDTO>> buscaMeusPedidos(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(pedidoService.buscaPedidoPorUsuario(username));
    }
}
