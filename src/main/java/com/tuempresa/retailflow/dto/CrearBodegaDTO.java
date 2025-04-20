package com.tuempresa.retailflow.dto;

import lombok.Data;
import java.util.List;

@Data
public class CrearBodegaDTO {
    private String nombre;
    private List<String> secciones = null;
}
