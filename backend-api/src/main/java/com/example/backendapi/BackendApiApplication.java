package com.example.backendapi;

import com.example.backendapi.model.Produto;
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

    @Bean
    CommandLineRunner initDatabase(ProdutoRepository repository) {
        return args -> {
            repository.deleteAll();

            Produto p1 = new Produto(
                    null,
                    "Placa de Vídeo RTX 4090",
                    "A placa de vídeo mais rápida do mercado.",
                    new BigDecimal("10500.00"),
                    "https://i.imgur.com/kGqTEbo.jpeg"
            );

            Produto p2 = new Produto(
                    null,
                    "Processador Ryzen 9 9900x",
                    "Performance extrema para multitarefa.",
                    new BigDecimal("3200.00"),
                    "https://i.imgur.com/qGGdPFN.jpeg"
            );

            repository.save(p1);
            repository.save(p2);
        };
    }
}
