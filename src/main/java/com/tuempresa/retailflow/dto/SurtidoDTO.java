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

    private List<SurtidoProductoDTO> productos;

    public SurtidoDTO() {
        this.fechaSurtido = LocalDateTime.now();
    }
}
