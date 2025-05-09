package com.tuempresa.retailflow.controller;

import com.tuempresa.retailflow.dto.ProductoDTO;
import com.tuempresa.retailflow.entity.Producto;
import com.tuempresa.retailflow.service.ProductoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@SecurityRequirement(name = "BearerAuth")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // ✅ Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductos() {
        return ResponseEntity.ok(productoService.obtenerProductos());
    }

    // ✅ Obtener los primeros 10 productos
    @GetMapping("/top")
    public ResponseEntity<List<Producto>> obtenerTopProductos() {
        return ResponseEntity.ok(productoService.obtenerTopProductos());
    }

    // ✅ Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    // ✅ Crear producto
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(productoService.crearProducto(dto));
    }

    // ✅ Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, dto));
    }

    // ✅ Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok("El producto fue eliminado");
    }

    @GetMapping("/total")
    public ResponseEntity<Long> totalProductos() {
        Long total = productoService.contarProductos();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/no-asignados")
    public ResponseEntity<List<Producto>> obtenerProductosNoAsignados() {
        List<Producto> productos = productoService.obtenerProductosNoAsignados();
        return ResponseEntity.ok(productos);
    }

}


