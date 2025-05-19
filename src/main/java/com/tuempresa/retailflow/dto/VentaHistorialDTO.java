package com.tuempresa.retailflow.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaHistorialDTO {
    private String nombreUsuario;
    private LocalDateTime fecha;
    private double total;
    private List<VentaProductoHistorialDTO> productos;
}
