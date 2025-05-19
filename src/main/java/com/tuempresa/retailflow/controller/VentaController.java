package com.tuempresa.retailflow.controller;

import com.tuempresa.retailflow.dto.CrearVentaDTO;
import com.tuempresa.retailflow.dto.VentaDTO;
import com.tuempresa.retailflow.service.VentaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ventas")
@SecurityRequirement(name = "BearerAuth")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<VentaDTO> crearVenta(@Valid @RequestBody CrearVentaDTO dto) {
        return ResponseEntity.ok(ventaService.crearVenta(dto));
    }
}

