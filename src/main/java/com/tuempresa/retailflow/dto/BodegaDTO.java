package com.tuempresa.retailflow.dto;

import lombok.Data;
import java.util.List;

@Data
public class BodegaDTO {
    private Long id;
    private String nombre;
    private List<SeccionDTO> secciones;  // Una bodega tiene muchas secciones
}
