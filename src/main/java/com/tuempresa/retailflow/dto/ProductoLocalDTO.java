package com.tuempresa.retailflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoLocalDTO {

    private Long id;
    private Long productoId;
    private Long LocalId;
    private Integer stock;
}
