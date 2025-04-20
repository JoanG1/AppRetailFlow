package com.tuempresa.retailflow.controller;

import com.tuempresa.retailflow.dto.SurtidoDTO;
import com.tuempresa.retailflow.entity.Surtido;
import com.tuempresa.retailflow.service.SurtidoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/{localId}")
    public ResponseEntity<Surtido> crearSurtido(@RequestBody SurtidoDTO surtidoDTO, @PathVariable Long localId) {
        Surtido nuevoSurtido = surtidoService.crearSurtido(surtidoDTO, localId );
        return new ResponseEntity<>(nuevoSurtido, HttpStatus.CREATED);
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Surtido>> obtenerHistorialSurtidos() {
        return ResponseEntity.ok(surtidoService.obtenerHistorialSurtidos());
    }

    @GetMapping("/top")
    public ResponseEntity<List<Surtido>> obtenerTopSurtidos() {
        return ResponseEntity.ok(surtidoService.obtenerTopSurtidos());
    }

    @GetMapping("/total")
    public ResponseEntity<Long> totalSurtidos() {
        Long total = surtidoService.contarSurtidos();
        return ResponseEntity.ok(total);
    }
}

