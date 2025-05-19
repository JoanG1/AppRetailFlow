package com.tuempresa.retailflow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CrearVentaDTO {

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long localId; // ✅ NUEVO: indispensable para buscar el ProductoLocal correcto

    @NotNull
    private List<CrearVentaDetalleDTO> productos;
}
