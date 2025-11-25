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
        if (produtoService.existsById(id)) {
            produtoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
