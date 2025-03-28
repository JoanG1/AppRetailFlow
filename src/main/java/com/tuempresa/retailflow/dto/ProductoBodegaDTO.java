package com.tuempresa.retailflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoBodegaDTO {
    private Long productoId;
    private Long bodegaId;
    private Long seccionId;
    private Integer stock;
}
