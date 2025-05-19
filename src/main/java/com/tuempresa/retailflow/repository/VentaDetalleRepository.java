package com.tuempresa.retailflow.repository;

import com.tuempresa.retailflow.entity.VentaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Long> {

    // 👇 Este método es el que necesitas
    List<VentaDetalle> findByVentaId(Long ventaId);
}
