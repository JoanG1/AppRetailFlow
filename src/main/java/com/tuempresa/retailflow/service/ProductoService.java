package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.ProductoDTO;
import com.tuempresa.retailflow.entity.Producto;
import com.tuempresa.retailflow.entity.ProductoBodega;
import com.tuempresa.retailflow.repository.ProductoRepository;
import com.tuempresa.retailflow.repository.ProductoBodegaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoBodegaRepository productoBodegaRepository;

    public ProductoService(ProductoRepository productoRepository, ProductoBodegaRepository productoBodegaRepository) {
        this.productoRepository = productoRepository;
        this.productoBodegaRepository = productoBodegaRepository;
    }

    // ✅ Obtener todos los productos
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    // ✅ Obtener producto por ID
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    // ✅ Crear nuevo producto
    public Producto crearProducto(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        return productoRepository.save(producto);
    }

    // ✅ Actualizar producto
    public Producto actualizarProducto(Long id, ProductoDTO dto) {
        Producto producto = obtenerProductoPorId(id);
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        return productoRepository.save(producto);
    }

    // ✅ Eliminar producto
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    // ✅ Obtener stock total del producto en todas las bodegas
    public int obtenerStockTotalPorProducto(Long productoId) {
        return productoBodegaRepository.findByProductoId(productoId)
                .stream()
                .mapToInt(ProductoBodega::getStock)
                .sum();
    }
}
