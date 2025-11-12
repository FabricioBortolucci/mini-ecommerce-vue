package com.example.backendapi.controller;

import com.example.backendapi.model.Produto;
import com.example.backendapi.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


    @GetMapping
    public List<Produto> listaProdutos(){
        return produtoService.buscaProdutos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscaProduto(@PathVariable Long id){
        return produtoService.buscaProdPorId(id);
    }

}
