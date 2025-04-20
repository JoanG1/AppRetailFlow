package com.tuempresa.retailflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrearLocalDTO {

    @NotBlank
    @NotNull
    private String nombre;
}
