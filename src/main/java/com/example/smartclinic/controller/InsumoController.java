package com.example.smartclinic.controller;

import com.example.smartclinic.model.Insumo;
import com.example.smartclinic.service.InsumoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/insumos")
public class InsumoController {

    private final InsumoService service;

    public InsumoController(InsumoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Insumo> all() {
        return service.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insumo> get(@PathVariable Long id) {
        return service.read(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Insumo> create(@Valid @RequestBody Insumo insumo) {
        Insumo saved = service.create(insumo);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Insumo> update(@PathVariable Long id, @Valid @RequestBody Insumo insumo) {
        try {
            Insumo updated = service.update(id, insumo);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verificar-estoque-minimo")
    public List<Insumo> verificarEstoqueMinimo() {
        return service.verificarEstoqueMinimo();
    }

    @GetMapping("/prever-reposicao")
    public Map<Long, Integer> preverReposicao() {
        return service.preverReposicao();
    }

    @GetMapping("/proximos-vencimentos")
    public List<Insumo> proximosVencimentos(@RequestParam(defaultValue = "30") int dias) {
        return service.insumosProximosVencimento(dias);
    }

    @GetMapping("/relatorio")
    public Map<String, Object> relatorio(@RequestParam(defaultValue = "30") int dias) {
        return service.gerarRelatorio(dias);
    }
}
