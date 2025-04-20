package com.tuempresa.retailflow.dto;


import lombok.Data;

import java.util.List;

@Data
public class LocalDTO {

    private Long id;
    private String nombre;
    private List<ProductoLocalDTO> productos; // Un local tiene varios productos
}
