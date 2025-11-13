package com.example.backendapi.controller;

import com.example.backendapi.model.Fornecedor;
import com.example.backendapi.services.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @Autowired
    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
    public List<Fornecedor> buscaFornecedores() {
        return fornecedorService.buscaTodosFornecedores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> buscaFornecedorPorId(@PathVariable Long id) {
        return fornecedorService.findFornecedorPorId(id);
    }
}
