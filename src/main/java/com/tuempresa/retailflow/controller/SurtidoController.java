package com.tuempresa.retailflow.controller;

import com.tuempresa.retailflow.dto.SurtidoDTO;
import com.tuempresa.retailflow.entity.Surtido;
import com.tuempresa.retailflow.service.SurtidoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/surtidos")
@SecurityRequirement(name = "BearerAuth")
public class SurtidoController {

    private final SurtidoService surtidoService;

    public SurtidoController(SurtidoService surtidoService) {
        this.surtidoService = surtidoService;
    }

    @PostMapping
    public ResponseEntity<Surtido> registrarSurtido(@RequestBody SurtidoDTO dto) {
        return ResponseEntity.ok(surtidoService.registrarSurtido(dto));
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Surtido>> obtenerHistorialSurtidos() {
        return ResponseEntity.ok(surtidoService.obtenerHistorialSurtidos());
    }
}

