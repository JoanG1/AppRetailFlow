package com.tuempresa.retailflow.repository;

import com.tuempresa.retailflow.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Override
    Optional<Producto> findById(Long Id);

    @Query("SELECT p FROM Producto p WHERE p.id NOT IN (SELECT pb.producto.id FROM ProductoBodega pb)")
    List<Producto> findProductosNoAsignados();
}

