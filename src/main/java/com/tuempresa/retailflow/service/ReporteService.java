package com.tuempresa.retailflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuempresa.retailflow.entity.Producto;
import com.tuempresa.retailflow.entity.Reporte;
import com.tuempresa.retailflow.repository.ProductoRepository;
import com.tuempresa.retailflow.repository.ReporteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final ProductoRepository productoRepository;

    /**
     * Obtiene los productos del inventario y genera un nuevo reporte.
     * @return Reporte generado y guardado en la base de datos
     */
    public Reporte generarNuevoReporteInventario() {
        List<Producto> productos = productoRepository.findAll();  // Obtiene los productos del inventario
        String contenidoReporte = convertirProductosAJson(productos);

        Reporte reporte = new Reporte();
        reporte.setTipo("inventario");
        reporte.setFechaGeneracion(LocalDateTime.now());
        reporte.setContenido(contenidoReporte);

        return reporteRepository.save(reporte);
    }

    /**
     * Obtiene todos los reportes almacenados en la base de datos.
     * @return Lista de reportes
     */
    public List<Reporte> obtenerTodosLosReportes() {
        return reporteRepository.findAll();
    }

    private String convertirProductosAJson(List<Producto> productos) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(productos);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al generar reporte", e);
        }
    }
}
