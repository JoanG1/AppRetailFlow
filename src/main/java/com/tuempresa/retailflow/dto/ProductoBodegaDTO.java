package com.tuempresa.retailflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class ProductoBodegaDTO {

    private Long id;
    private Long productoId;
    private Long seccionId;
    private Integer stock;
}
