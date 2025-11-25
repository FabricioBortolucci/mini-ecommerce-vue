package com.example.backendapi.services;

import com.example.backendapi.dto.*;
import com.example.backendapi.model.Pedido;
import com.example.backendapi.model.PedidoItem;
import com.example.backendapi.model.Produto;
import com.example.backendapi.model.Usuario;
import com.example.backendapi.model.enums.StatusPedido;
import com.example.backendapi.repositories.PedidoRepository;
import com.example.backendapi.repositories.ProdutoRepository;
import com.example.backendapi.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }


    @Transactional
    public Pedido criarPedido(PedidoRequestDTO request, String nomeUsuarioLogado) {

        Usuario user = usuarioRepository.findByUsername(nomeUsuarioLogado)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Pedido novoPedido = Pedido.builder()
                .usuario(user)
                .dataPedido(LocalDateTime.now())
                .status(StatusPedido.PROCESSANDO)
                .build();

        BigDecimal valorTotalDoPedido = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO it : request.itens()) {

            Produto produto = produtoRepository.findById(it.produtoId()).
                    orElseThrow(() -> new EntityNotFoundException("Produto not found id:" + it.produtoId()));

            PedidoItem pedidoItem = PedidoItem.builder()
                    .produto(produto)
                    .quantidade(it.quantidade())
                    .precoUnitario(produto.getPreco())
                    .build();

            BigDecimal subTotalItem = produto.getPreco().multiply(new BigDecimal(it.quantidade()));
            valorTotalDoPedido = valorTotalDoPedido.add(subTotalItem);


            novoPedido.adicionarItem(pedidoItem);

        }
        novoPedido.setValorTotal(valorTotalDoPedido);
        return pedidoRepository.save(novoPedido);
    }


    public List<PedidoDTO> buscaPedidoPorUsuario(String usuarioLogado) {
        Usuario user = usuarioRepository.findByUsername(usuarioLogado)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Pedido> pedidos = pedidoRepository.findByUsuario(user);
        return  pedidos.stream()
                .map(this::convertToPedidoDTO)
                .toList();
    }


    private PedidoDTO convertToPedidoDTO(Pedido pedido) {
        List<PedidoItemDTO> itensDTO = pedido.getItens().stream()
                .map(this::convertToPedidoItemDTO)
                .toList();

        return new PedidoDTO(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getValorTotal(),
                pedido.getStatus(),
                itensDTO
        );
    }


    private PedidoItemDTO convertToPedidoItemDTO(PedidoItem item) {

        Produto produto = item.getProduto();

        ProdutoDTO produtoDTO = new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getImageUrl(),
                null,
                null,
                null
        );

        return new PedidoItemDTO(
                item.getId(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                produtoDTO
        );
    }
}
