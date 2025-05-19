package com.tuempresa.retailflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VentaDTO {
    private Long id;
    private LocalDateTime fecha;
    private String nombreUsuario;
    private double total;
    private List<VentaDetalleDTO> detalles;
}
