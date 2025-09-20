package com.example.smartclinic.dao;

import com.example.smartclinic.model.Insumo;
import com.example.smartclinic.repository.InsumoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InsumoDAO {

    private final InsumoRepository repo;

    public InsumoDAO(InsumoRepository repo) {
        this.repo = repo;
    }

    public Insumo save(Insumo insumo) {
        return repo.save(insumo);
    }

    public Optional<Insumo> findById(Long id) {
        return repo.findById(id);
    }

    public List<Insumo> findAll() {
        return repo.findAll();
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public List<Insumo> findByCategoria(String categoria) {
        return repo.findByCategoria(categoria);
    }

    public List<Insumo> findByFornecedor(String fornecedor) {
        return repo.findByFornecedor(fornecedor);
    }
}
