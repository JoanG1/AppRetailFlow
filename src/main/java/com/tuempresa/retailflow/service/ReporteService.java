package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.entity.Reporte;
import com.tuempresa.retailflow.repository.ReporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;

    public ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public List<Reporte> generarReporteInventario() {
        return reporteRepository.findByTipo("inventario");
    }

    public List<Reporte> generarReporteSurtidos() {
        return reporteRepository.findByTipo("surtidos");
    }
}
