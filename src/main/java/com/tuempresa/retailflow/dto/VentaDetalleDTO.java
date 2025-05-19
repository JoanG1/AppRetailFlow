package com.tuempresa.retailflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaDetalleDTO {
    private String nombreProducto;
    private String nombreLocal;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
}
