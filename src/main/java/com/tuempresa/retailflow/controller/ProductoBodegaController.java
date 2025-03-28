package com.tuempresa.retailflow.controller;

import com.tuempresa.retailflow.dto.ProductoBodegaDTO;
import com.tuempresa.retailflow.entity.ProductoBodega;
import com.tuempresa.retailflow.service.ProductoBodegaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos-bodega")
@AllArgsConstructor
public class ProductoBodegaController {

    private final ProductoBodegaService productoBodegaService;

    // ✅ Obtener productos por bodega
    @GetMapping("/bodega/{bodegaId}")
    public ResponseEntity<List<ProductoBodega>> obtenerProductosPorBodega(@PathVariable Long bodegaId) {
        return ResponseEntity.ok(productoBodegaService.obtenerProductosPorBodega(bodegaId));
    }

    // ✅ Obtener productos por bodega y sección
    @GetMapping("/bodega/{bodegaId}/seccion/{seccionId}")
    public ResponseEntity<List<ProductoBodega>> obtenerProductosPorBodegaYSeccion(
            @PathVariable Long bodegaId, @PathVariable Long seccionId) {
        return ResponseEntity.ok(productoBodegaService.obtenerProductosPorBodegaYSeccion(bodegaId, seccionId));
    }

    // ✅ Asignar stock a un producto en una bodega y sección
    @PostMapping
    public ResponseEntity<ProductoBodega> asignarProductoABodega(@RequestBody ProductoBodegaDTO dto) {
        return ResponseEntity.ok(productoBodegaService.asignarProductoABodega(dto));
    }

    // ✅ Obtener stock total de un producto
    @GetMapping("/stock/{productoId}")
    public ResponseEntity<Integer> obtenerStockTotalPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(productoBodegaService.obtenerStockTotalPorProducto(productoId));
    }

    // ✅ Eliminar un producto de una bodega
    @DeleteMapping("/{productoBodegaId}")
    public ResponseEntity<Void> eliminarProductoDeBodega(@PathVariable Long productoBodegaId) {
        productoBodegaService.eliminarProductoDeBodega(productoBodegaId);
        return ResponseEntity.noContent().build();
    }
}

