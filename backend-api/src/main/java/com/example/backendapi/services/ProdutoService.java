package com.example.backendapi.services;

import com.example.backendapi.dto.ProdutoDTO;
import com.example.backendapi.model.Fornecedor;
import com.example.backendapi.model.Produto;
import com.example.backendapi.repositories.FornecedorRepository;
import com.example.backendapi.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;

    public ProdutoService(ProdutoRepository produtoRepository, FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<Produto> buscaProdutos() {
        return produtoRepository.findAllByAtivo(true);
    }

    public List<Produto> buscaProdutosAdmin() {
        return produtoRepository.findAll();
    }

    public ResponseEntity<Produto> buscaProdPorId(Long id) {
      return ResponseEntity.ok(produtoRepository.findById(id).orElseThrow());
    }

    public Optional<Produto> buscaProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Transactional
    public Produto criarProduto(ProdutoDTO produto) {
        Fornecedor fornecedor = fornecedorRepository.findById(produto.fornecedorId()).orElseThrow();

        Produto prod = new Produto(null,
                produto.nome(),
                produto.descricao(),
                produto.preco(),
                produto.imageUrl(),
                fornecedor,
                true);

       return produtoRepository.save(prod);
    }


    public Produto save(Produto produto) {
       return produtoRepository.save(produto);
    }

    public boolean existsById(Long id) {
        return produtoRepository.existsById(id);
    }

    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }
}
