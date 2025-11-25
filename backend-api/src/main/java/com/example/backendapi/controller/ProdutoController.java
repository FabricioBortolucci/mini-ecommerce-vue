package com.example.backendapi.controller;

import com.example.backendapi.dto.ProdutoDTO;
import com.example.backendapi.model.Fornecedor;
import com.example.backendapi.model.Produto;
import com.example.backendapi.services.FornecedorService;
import com.example.backendapi.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final FornecedorService fornecedorService;

    @Autowired
    public ProdutoController(ProdutoService produtoService, FornecedorService fornecedorService) {
        this.produtoService = produtoService;
        this.fornecedorService = fornecedorService;
    }


    @GetMapping
    public List<Produto> listaProdutos() {
        return produtoService.buscaProdutos();
    }

    @GetMapping("/admin")
    public List<Produto> listaProdutosAdmin() {
        return produtoService.buscaProdutosAdmin();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscaProduto(@PathVariable Long id) {
        return produtoService.buscaProdPorId(id);
    }


    @PostMapping("/criar")
    public ResponseEntity<Produto> criarProduto(@RequestBody ProdutoDTO produto) {
        Produto produtoNovo = produtoService.criarProduto(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoNovo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        return produtoService.buscaProdutoPorId(id)
                .map(produto -> {
                    if (dto.fornecedorId() != null) {
                        Fornecedor novoFornecedor = fornecedorService.buscaFornecedorPorId(dto.fornecedorId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fornecedor não encontrado"));
                        produto.setFornecedor(novoFornecedor);
                    }
                    produto.setNome(dto.nome());
                    produto.setDescricao(dto.descricao());
                    produto.setPreco(dto.preco());
                    produto.setImageUrl(dto.imageUrl());
                    return ResponseEntity.ok(produtoService.save(produto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        return produtoService.buscaProdutoPorId(id)
                .map(produto -> {
                    produto.setAtivo(false);
                    produtoService.save(produto);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<Void> ativarProduto(@PathVariable Long id) {
        return produtoService.buscaProdutoPorId(id)
                .map(produto -> {
                    produto.setAtivo(true);
                    produtoService.save(produto);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
