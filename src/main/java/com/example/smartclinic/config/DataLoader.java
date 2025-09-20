package com.example.smartclinic.config;

import com.example.smartclinic.model.Insumo;
import com.example.smartclinic.repository.InsumoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(InsumoRepository repo) {
        return args -> {
            repo.save(Insumo.builder().nome("Soro Fisiológico 0.9% 500ml").quantidade(50).quantidadeMinima(20)
                    .dataValidade(LocalDate.now().plusDays(200)).categoria("Soluções").fornecedor("Fornecedor A").build());
            repo.save(Insumo.builder().nome("Álcool 70% 1L").quantidade(10).quantidadeMinima(30)
                    .dataValidade(LocalDate.now().plusDays(15)).categoria("Antissépticos").fornecedor("Fornecedor B").build());
            repo.save(Insumo.builder().nome("Luvas Descartáveis").quantidade(100).quantidadeMinima(50)
                    .dataValidade(LocalDate.now().plusDays(400)).categoria("EPI").fornecedor("Fornecedor A").build());
            repo.save(Insumo.builder().nome("Vacina X").quantidade(5).quantidadeMinima(20)
                    .dataValidade(LocalDate.now().plusDays(5)).categoria("Imunobiológicos").fornecedor("Fornecedor C").build());
        };
    }
}
