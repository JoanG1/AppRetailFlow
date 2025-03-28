package com.tuempresa.retailflow.controller;

import com.tuempresa.retailflow.dto.BodegaDTO;
import com.tuempresa.retailflow.dto.CrearBodegaDTO;
import com.tuempresa.retailflow.service.BodegaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/bodegas")
@AllArgsConstructor
public class BodegaController {

    private final BodegaService bodegaService;

    // ✅ Obtener todas las bodegas con sus secciones
    @GetMapping
    public ResponseEntity<List<BodegaDTO>> obtenerTodasLasBodegas() {
        return ResponseEntity.ok(bodegaService.obtenerTodasLasBodegas());
    }

    // ✅ Obtener bodega por ID
    @GetMapping("/{id}")
    public ResponseEntity<BodegaDTO> obtenerBodegaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(bodegaService.obtenerBodegaPorId(id));
    }

    // ✅ Crear nueva bodega
    @PostMapping
    public ResponseEntity<BodegaDTO> crearBodega(@RequestBody CrearBodegaDTO dto) {
        return ResponseEntity.ok(bodegaService.crearBodega(dto));
    }

    // ✅ Actualizar bodega
    @PutMapping("/{id}")
    public ResponseEntity<BodegaDTO> actualizarBodega(@PathVariable Long id, @RequestBody BodegaDTO dto) {
        return ResponseEntity.ok(bodegaService.actualizarBodega(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBodega(@PathVariable Long id) {
        try {
            bodegaService.eliminarBodega(id);
            return ResponseEntity.noContent().build(); // ✅ 204 NO CONTENT
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).build(); // ❌ Error manejado correctamente
        }
    }
}

