package com.tuempresa.retailflow.controller;

import com.tuempresa.retailflow.entity.Reporte;
import com.tuempresa.retailflow.service.ReporteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportes")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;


    @PostMapping("/inventario")
    public ResponseEntity<Reporte> generarReporteInventario() {
        Reporte reporte = reporteService.generarNuevoReporteInventario();
        return ResponseEntity.ok(reporte);
    }

    @GetMapping
    public ResponseEntity<List<Reporte>> obtenerTodosLosReportes() {
        List<Reporte> reportes = reporteService.obtenerTodosLosReportes();
        return ResponseEntity.ok(reportes);
    }
}

