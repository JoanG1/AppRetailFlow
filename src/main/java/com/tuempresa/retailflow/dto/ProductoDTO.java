package com.tuempresa.retailflow.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductoDTO {
    private Long id;
    private String nombre;
    private Double precio;
    private int stock;
}