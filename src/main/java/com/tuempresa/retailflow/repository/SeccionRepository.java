package com.tuempresa.retailflow.repository;

import com.tuempresa.retailflow.entity.Seccion;
import com.tuempresa.retailflow.entity.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeccionRepository extends JpaRepository<Seccion, Long> {

    // ✅ Obtener todas las secciones de una bodega específica
    List<Seccion> findByBodegaId(Long bodegaId);

    // ✅ Verificar si existe una sección con el mismo nombre dentro de la misma bodega
    boolean existsByNombreAndBodega(String nombre, Bodega bodega);
}
