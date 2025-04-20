package com.tuempresa.retailflow.dto;

import lombok.Data;
import java.util.List;

@Data
public class SeccionDTO {

    private String nombre;
    private Long bodegaId; // Para saber a qué bodega pertenece
}

