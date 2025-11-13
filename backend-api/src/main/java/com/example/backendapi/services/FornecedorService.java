package com.example.backendapi.services;

import com.example.backendapi.model.Fornecedor;
import com.example.backendapi.repositories.FornecedorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public ResponseEntity<Fornecedor> findFornecedorPorId(Long id) {
        return ResponseEntity.ok(fornecedorRepository.findById(id).orElseThrow());
    }

    public List<Fornecedor> buscaTodosFornecedores() {
        return fornecedorRepository.findAll();
    }
}
