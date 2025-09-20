package com.example.smartclinic.service;

import com.example.smartclinic.dao.InsumoDAO;
import com.example.smartclinic.model.Insumo;
import com.example.smartclinic.repository.InsumoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Lógica de negócio — contém métodos relevantes:
 * - verificarEstoqueMinimo
 * - preverReposicao (quantidade sugerida)
 * - insumosProximosVencimento
 * - gerarRelatorio (por categoria/fornecedor/vencimento)
 */
@Service
public class InsumoService {

    private final InsumoDAO dao;
    private final InsumoRepository repo;

    public InsumoService(InsumoDAO dao, InsumoRepository repo) {
        this.dao = dao;
        this.repo = repo;
    }

    // CRUD simples delegando para DAO
    public Insumo create(Insumo i) { return dao.save(i); }
    public Optional<Insumo> read(Long id) { return dao.findById(id); }
    public List<Insumo> readAll() { return dao.findAll(); }
    public Insumo update(Long id, Insumo updated) {
        Insumo existing = dao.findById(id).orElseThrow(() -> new RuntimeException("Insumo não encontrado: " + id));
        existing.setNome(updated.getNome());
        existing.setQuantidade(updated.getQuantidade());
        existing.setQuantidadeMinima(updated.getQuantidadeMinima());
        existing.setDataValidade(updated.getDataValidade());
        existing.setCategoria(updated.getCategoria());
        existing.setFornecedor(updated.getFornecedor());
        return dao.save(existing);
    }
    public void delete(Long id) { dao.deleteById(id); }

    // 1) Verificação de estoque mínimo
    public List<Insumo> verificarEstoqueMinimo() {
        return repo.findAll().stream()
                .filter(i -> i.getQuantidade() != null && i.getQuantidadeMinima() != null && i.getQuantidade() < i.getQuantidadeMinima())
                .collect(Collectors.toList());
    }

    // 2) Previsão de reposição (sugere quantidade para repor até o dobro do mínimo)
    public Map<Long, Integer> preverReposicao() {
        Map<Long, Integer> sugestoes = new HashMap<>();
        for (Insumo i : repo.findAll()) {
            if (i.getQuantidade() == null || i.getQuantidadeMinima() == null) continue;
            if (i.getQuantidade() < i.getQuantidadeMinima()) {
                int suggested = Math.max(0, i.getQuantidadeMinima() * 2 - i.getQuantidade());
                sugestoes.put(i.getId(), suggested);
            }
        }
        return sugestoes;
    }

    // 3) Identificar insumos com vencimento em menos de X dias
    public List<Insumo> insumosProximosVencimento(int dias) {
        LocalDate limite = LocalDate.now().plusDays(dias);
        return repo.findByDataValidadeBefore(limite);
    }

    // 4) Relatório simples: agrupado por categoria, fornecedor e vencimentos próximos (menor que 'dias')
    public Map<String, Object> gerarRelatorio(int diasParaVencimento) {
        Map<String, Object> report = new LinkedHashMap<>();
        List<Insumo> todos = repo.findAll();

        Map<String, Long> porCategoria = todos.stream()
                .collect(Collectors.groupingBy(i -> Optional.ofNullable(i.getCategoria()).orElse("N/A"), Collectors.counting()));
        Map<String, Long> porFornecedor = todos.stream()
                .collect(Collectors.groupingBy(i -> Optional.ofNullable(i.getFornecedor()).orElse("N/A"), Collectors.counting()));
        List<Insumo> proximosVenc = insumosProximosVencimento(diasParaVencimento);

        report.put("total", todos.size());
        report.put("porCategoria", porCategoria);
        report.put("porFornecedor", porFornecedor);
        report.put("proximosVencimentos", proximosVenc);
        return report;
    }
}
