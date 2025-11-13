package com.example.backendapi.services;

import com.example.backendapi.model.Produto;
import com.example.backendapi.repositories.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> buscaProdutos() {
        return produtoRepository.findAll();
    }

    public ResponseEntity<Produto> buscaProdPorId(Long id) {
      return ResponseEntity.ok(produtoRepository.findById(id).orElseThrow());
    }
}
