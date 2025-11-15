package com.example.backendapi;

import com.example.backendapi.model.Fornecedor;
import com.example.backendapi.model.Produto;
import com.example.backendapi.repositories.FornecedorRepository;
import com.example.backendapi.repositories.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class BackendApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApiApplication.class, args);
    }

    /*@Bean
    CommandLineRunner initDatabase(ProdutoRepository repository, FornecedorRepository fornecedorRepository) {
        return args -> {
            repository.deleteAll();
            fornecedorRepository.deleteAll();

            Fornecedor fornecedor1 = new Fornecedor(null,
                    "Ernesto",
                    "99723245",
                    "ernesto.borracha@yahoo.com",
                    "11.111.111/0001-11",
                    "https://i.imgur.com/w7Su3GO.png");

            Fornecedor fornecedor2 = new Fornecedor(null,
                    "Lauro",
                    "99432810",
                    "lauro.chimp@hotmail.com",
                    "22.222.222/0001-22",
                    "https://i.imgur.com/GgsqNuz.jpeg");

            fornecedorRepository.save(fornecedor1);
            fornecedorRepository.save(fornecedor2);

            Produto p1 = new Produto(
                    null,
                    "Placa de Vídeo RTX 4090",
                    "A placa de vídeo mais rápida do mercado.",
                    new BigDecimal("10500.00"),
                    "https://i.imgur.com/kGqTEbo.jpeg",
                    fornecedor2
            );

            Produto p2 = new Produto(
                    null,
                    "Processador Ryzen 9 9900x",
                    "Performance extrema para multitarefa.",
                    new BigDecimal("3200.00"),
                    "https://i.imgur.com/qGGdPFN.jpeg",
                    fornecedor1
            );

            repository.save(p1);
            repository.save(p2);
        };
    }*/
}
