package com.tuempresa.retailflow.controller;

import com.tuempresa.retailflow.entity.Reporte;
import com.tuempresa.retailflow.service.ReporteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportes")
@SecurityRequirement(name = "BearerAuth")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/inventario")
    public ResponseEntity<List<Reporte>> generarReporteInventario() {
        return ResponseEntity.ok(reporteService.generarReporteInventario());
    }

    @GetMapping("/surtidos")
    public ResponseEntity<List<Reporte>> generarReporteSurtidos() {
        return ResponseEntity.ok(reporteService.generarReporteSurtidos());
    }
}

