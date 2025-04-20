package com.tuempresa.retailflow.controller;


import com.tuempresa.retailflow.dto.*;
import com.tuempresa.retailflow.entity.Local;
import com.tuempresa.retailflow.service.LocalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/Locales")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class LocalController {

    private final LocalService localService;

    // ✅ Obtener todos los locales
    @GetMapping
    public ResponseEntity<List<Local>> obtenerTodasLosLocales() {
        return ResponseEntity.ok(localService.obtenerTodasLosLocales());
    }

    // ✅ Obtener bodega por ID
    @GetMapping("/{id}")
    public ResponseEntity<List<Local>> obtenerLocalPorId(@PathVariable Long id) {
        return ResponseEntity.ok(localService.obtenerLocalPorId(id));
    }

    // ✅ Crear nueva bodega
    @PostMapping
    public ResponseEntity<Local> crearLocal(@RequestBody CrearLocalDTO dto) {
        return ResponseEntity.ok(localService.crearLocal(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLocal(@PathVariable Long id) {
        try {
            localService.eliminarLocal(id);
            return ResponseEntity.noContent().build(); // ✅ 204 NO CONTENT
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).build(); // ❌ Error manejado correctamente
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Local> actualizarLocal(@PathVariable Long id, @RequestBody CrearLocalDTO dto) {

        return ResponseEntity.ok(localService.actualizarLocal(id, dto));

    }

    @GetMapping("/total")
    public ResponseEntity<Long> totalLocales() {
        Long total = localService.contarLocales();
        return ResponseEntity.ok(total);
    }


}
