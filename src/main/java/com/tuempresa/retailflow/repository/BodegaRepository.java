package com.tuempresa.retailflow.repository;

import com.tuempresa.retailflow.entity.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BodegaRepository extends JpaRepository<Bodega, Long> {


    Optional<Bodega> getBodegaById(Long id);
}