package com.tuempresa.retailflow.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearVentaDetalleDTO {
    @NotNull
    private Long productoId;

    @Min(1)
    private int cantidad;
}

