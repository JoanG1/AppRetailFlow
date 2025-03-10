package com.tuempresa.retailflow.repository;

import com.tuempresa.retailflow.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByTipo(String tipo);
}
