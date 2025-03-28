package com.tuempresa.retailflow.controller;

import com.tuempresa.retailflow.dto.SeccionDTO;
import com.tuempresa.retailflow.service.SeccionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secciones")
@AllArgsConstructor
public class SeccionController {

    private final SeccionService seccionService;

    // ✅ Obtener todas las secciones
    @GetMapping
    public ResponseEntity<List<SeccionDTO>> obtenerTodasLasSecciones() {
        return ResponseEntity.ok(seccionService.obtenerTodasLasSecciones());
    }

    // ✅ Obtener sección por ID
    @GetMapping("/{id}")
    public ResponseEntity<SeccionDTO> obtenerSeccionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(seccionService.obtenerSeccionPorId(id));
    }

    // ✅ Crear nueva sección
    @PostMapping
    public ResponseEntity<SeccionDTO> crearSeccion(@RequestBody SeccionDTO dto) {
        return ResponseEntity.ok(seccionService.crearSeccion(dto));
    }

    // ✅ Actualizar sección
    @PutMapping("/{id}")
    public ResponseEntity<SeccionDTO> actualizarSeccion(@PathVariable Long id, @RequestBody SeccionDTO dto) {
        return ResponseEntity.ok(seccionService.actualizarSeccion(id, dto));
    }

    // ✅ Eliminar sección
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSeccion(@PathVariable Long id) {
        seccionService.eliminarSeccion(id);
        return ResponseEntity.noContent().build();
    }
}
