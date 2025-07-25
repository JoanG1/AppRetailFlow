package com.tuempresa.retailflow.controller;

import com.tuempresa.retailflow.dto.CrearVentaDTO;
import com.tuempresa.retailflow.dto.VentaDTO;
import com.tuempresa.retailflow.dto.VentaHistorialDTO;
import com.tuempresa.retailflow.service.VentaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 📄 NUEVO ENDPOINT: Obtener historial de ventas
    @GetMapping("/historial")
    public ResponseEntity<List<VentaHistorialDTO>> obtenerHistorialVentas() {
        List<VentaHistorialDTO> historial = ventaService.obtenerHistorialDeVentas();
        return ResponseEntity.ok(historial);
    }
}

