package com.example.smartclinic.service;

import com.example.smartclinic.model.Insumo;
import com.example.smartclinic.repository.InsumoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InsumoServiceTest {

    @Autowired
    private InsumoService service;

    @Autowired
    private InsumoRepository repo;

    @BeforeEach
    public void setup() {
        repo.deleteAll();
        repo.save(Insumo.builder().nome("A").quantidade(5).quantidadeMinima(10).dataValidade(LocalDate.now().plusDays(2)).categoria("C1").fornecedor("F1").build());
        repo.save(Insumo.builder().nome("B").quantidade(50).quantidadeMinima(20).dataValidade(LocalDate.now().plusDays(60)).categoria("C2").fornecedor("F2").build());
    }

    @Test
    public void testVerificarEstoqueMinimo() {
        List<com.example.smartclinic.model.Insumo> baixos = service.verificarEstoqueMinimo();
        assertThat(baixos).hasSize(1);
        assertThat(baixos.get(0).getNome()).isEqualTo("A");
    }

    @Test
    public void testPreverReposicao() {
        Map<Long, Integer> sugestoes = service.preverReposicao();
        assertThat(sugestoes).isNotEmpty();
    }

    @Test
    public void testInsumosProximosVencimento() {
        List<com.example.smartclinic.model.Insumo> proximos = service.insumosProximosVencimento(10);
        assertThat(proximos).hasSize(1);
    }

    @Test
    public void testGerarRelatorio() {
        Map<String, Object> rel = service.gerarRelatorio(30);
        assertThat(rel).containsKeys("total", "porCategoria", "porFornecedor", "proximosVencimentos");
    }
}
