package com.tuempresa.retailflow.controller;


import com.tuempresa.retailflow.dto.EditarProductoLocalDTO;
import com.tuempresa.retailflow.dto.ProductoLocalDTO;
import com.tuempresa.retailflow.entity.ProductoLocal;
import com.tuempresa.retailflow.service.ProductoLocalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos-local")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class ProductoLocalController {

    private final ProductoLocalService productoLocalService;

    // ✅ Obtener productos por bodega
    @GetMapping("/local/{localId}")
    public ResponseEntity<List<ProductoLocalDTO>> obtenerProductosPorLocal (@PathVariable Long localId) {
        return ResponseEntity.ok(productoLocalService.obtenerProductosPorLocal(localId));
    }

    // ✅ Obtener stock total de un producto
    @GetMapping("/stock/{productoId}")
    public ResponseEntity<Integer> obtenerStockTotalPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(productoLocalService.obtenerStockTotalPorProducto(productoId));
    }

    // ✅ Eliminar un producto de una bodega
    @DeleteMapping("/{productoLocalId}")
    public ResponseEntity<Void> eliminarProductoDeLocal(@PathVariable Long productoLocalId) {
        productoLocalService.eliminarProductoDeLocal(productoLocalId);
        return ResponseEntity.noContent().build();
    }

    //Crear un producto local y asignarlo a el local
    @PostMapping
    public ResponseEntity<String> crearProductoLocal(@RequestBody ProductoLocalDTO dto) {
        productoLocalService.crearProductoLocal(dto);
        return ResponseEntity.ok("ProductoLocal creado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoLocal> actualizarProductoLocal(@PathVariable Long id, @RequestBody EditarProductoLocalDTO dto) {
        return ResponseEntity.ok(productoLocalService.actualizarProductoLocal(id, dto));
    }

}
