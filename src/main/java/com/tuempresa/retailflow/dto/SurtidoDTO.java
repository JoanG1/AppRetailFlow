package com.tuempresa.retailflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SurtidoDTO {
    private LocalDateTime fechaSurtido;
    private List<ProductoSurtidoDTO> productos = new ArrayList<>();

    public SurtidoDTO() {
        this.fechaSurtido = LocalDateTime.now();
    }
}
