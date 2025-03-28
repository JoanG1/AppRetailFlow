package com.tuempresa.retailflow.dto;

import lombok.Data;
import java.util.List;

@Data
public class SeccionDTO {
    private Long id;
    private String nombre;
    private Long bodegaId; // Para saber a qué bodega pertenece
    private List<ProductoBodegaDTO> productos; // Una sección tiene varios productos
}

