package com.tuempresa.retailflow.controller;

import com.tuempresa.retailflow.dto.SeccionDTO;
import com.tuempresa.retailflow.entity.Seccion;
import com.tuempresa.retailflow.service.SeccionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secciones")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class SeccionController {

    private final SeccionService seccionService;

    // ✅ Obtener todas las secciones
    @GetMapping
    public ResponseEntity<List<Seccion>> obtenerTodasLasSecciones() {
        return ResponseEntity.ok(seccionService.obtenerTodasLasSecciones());
    }


    // ✅ Obtener sección por ID
    @GetMapping("/{id}")
    public ResponseEntity<Seccion> obtenerSeccionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(seccionService.obtenerSeccionPorId(id));
    }

    // ✅ Crear nueva sección
    @PostMapping
    public ResponseEntity<Seccion> crearSeccion(@RequestBody SeccionDTO dto) {
        return ResponseEntity.ok(seccionService.crearSeccion(dto));
    }

    // ✅ Actualizar sección
    @PutMapping("/{id}")
    public ResponseEntity<Seccion> actualizarSeccion(@PathVariable Long id, @RequestBody SeccionDTO dto) {
        return ResponseEntity.ok(seccionService.actualizarSeccion(id, dto));
    }

    // ✅ Eliminar sección
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSeccion(@PathVariable Long id) {
        seccionService.eliminarSeccion(id);
        return ResponseEntity.noContent().build();
    }

    //✅ Traer secciones de una bodega
    @GetMapping("/bodega/{idBodega}")
    public ResponseEntity<List<Seccion>> obtenerSeccionPorBodega(@PathVariable Long idBodega) {
        return ResponseEntity.ok(seccionService.obtenerSeccionPorBodegaId(idBodega));
    }
}
