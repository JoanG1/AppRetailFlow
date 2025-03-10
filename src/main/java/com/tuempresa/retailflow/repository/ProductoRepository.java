package com.tuempresa.retailflow.repository;

import com.tuempresa.retailflow.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

