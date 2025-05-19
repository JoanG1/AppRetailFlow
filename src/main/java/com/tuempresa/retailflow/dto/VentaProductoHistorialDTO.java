package com.tuempresa.retailflow.dto;

import lombok.Data;

@Data
public class VentaProductoHistorialDTO {
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
}
