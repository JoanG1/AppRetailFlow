package com.tuempresa.retailflow.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ReporteDTO {
    private String titulo;
    private List<String> datos;
}
