package com.example.smartclinic.repository;

import com.example.smartclinic.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
    List<Insumo> findByCategoria(String categoria);
    List<Insumo> findByFornecedor(String fornecedor);
    List<Insumo> findByDataValidadeBefore(LocalDate date);
}
