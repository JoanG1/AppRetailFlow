package com.tuempresa.retailflow.controller;

import com.tuempresa.retailflow.dto.BodegaDTO;
import com.tuempresa.retailflow.dto.CrearBodegaDTO;
import com.tuempresa.retailflow.entity.Bodega;
import com.tuempresa.retailflow.service.BodegaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/bodegas")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class BodegaController {

    private final BodegaService bodegaService;

    // ✅ Obtener todas las bodegas con sus secciones
    @GetMapping
    public ResponseEntity<List<Bodega>> obtenerTodasLasBodegas() {
        return ResponseEntity.ok(bodegaService.obtenerTodasLasBodegas());
    }

    // ✅ Obtener bodega por ID
    @GetMapping("/{id}")
    public ResponseEntity<Bodega> obtenerBodegaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(bodegaService.obtenerBodegaPorId(id));
    }

    // ✅ Crear nueva bodega
    @PostMapping
    public ResponseEntity<Bodega> crearBodega(@RequestBody CrearBodegaDTO dto) {
        return ResponseEntity.ok(bodegaService.crearBodega(dto));
    }

    // ✅ Actualizar bodega
    @PutMapping("/{id}")
    public ResponseEntity<Bodega> actualizarBodega(@PathVariable Long id, @RequestBody BodegaDTO dto) {
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

    // ✅ Crear nueva bodega vacia
    @PostMapping("/vacia")
    public ResponseEntity<Bodega> crearBodegaVacia(@RequestBody BodegaDTO dto) {
        return ResponseEntity.ok(bodegaService.crearBodegaVacia(dto));
    }

    @GetMapping("/total")
    public ResponseEntity<Long> totalProductos() {
        Long total = bodegaService.contarBodegas();
        return ResponseEntity.ok(total);
    }


}

