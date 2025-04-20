package com.tuempresa.retailflow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditarProductoLocalDTO {

    @NotNull
    private Integer stock;

}
