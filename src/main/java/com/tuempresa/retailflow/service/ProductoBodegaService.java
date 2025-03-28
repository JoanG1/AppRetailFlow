package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.ProductoBodegaDTO;
import com.tuempresa.retailflow.entity.Bodega;
import com.tuempresa.retailflow.entity.Producto;
import com.tuempresa.retailflow.entity.ProductoBodega;
import com.tuempresa.retailflow.entity.Seccion;
import com.tuempresa.retailflow.repository.BodegaRepository;
import com.tuempresa.retailflow.repository.ProductoBodegaRepository;
import com.tuempresa.retailflow.repository.ProductoRepository;
import com.tuempresa.retailflow.repository.SeccionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoBodegaService {

    private final ProductoBodegaRepository productoBodegaRepository;
    private final ProductoRepository productoRepository;
    private final BodegaRepository bodegaRepository;
    private final SeccionRepository seccionRepository;

    // ✅ Obtener productos por bodega
    public List<ProductoBodega> obtenerProductosPorBodega(Long bodegaId) {
        return productoBodegaRepository.findByBodegaId(bodegaId);
    }

    // ✅ Obtener productos por bodega y sección
    public List<ProductoBodega> obtenerProductosPorBodegaYSeccion(Long bodegaId, Long seccionId) {
        return productoBodegaRepository.findByBodegaIdAndSeccionId(bodegaId, seccionId);
    }

    @Transactional
    public ProductoBodega asignarProductoABodega(ProductoBodegaDTO dto) {
        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser nulo ni negativo.");
        }

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        Bodega bodega = bodegaRepository.findById(dto.getBodegaId())
                .orElseThrow(() -> new EntityNotFoundException("Bodega no encontrada"));
        Seccion seccion = seccionRepository.findById(dto.getSeccionId())
                .orElseThrow(() -> new EntityNotFoundException("Sección no encontrada"));

        // 🔍 Buscar si ya existe el producto en la bodega y sección
        ProductoBodega productoBodega = productoBodegaRepository.findByProductoIdAndBodegaIdAndSeccionId(
                dto.getProductoId(), dto.getBodegaId(), dto.getSeccionId()).orElse(null);

        if (productoBodega == null) {
            productoBodega = new ProductoBodega();
            productoBodega.setProducto(producto);
            productoBodega.setBodega(bodega);
            productoBodega.setSeccion(seccion);
            productoBodega.setStock(dto.getStock());
        } else {
            // 🔄 Si ya existe, actualizamos solo el stock
            productoBodega.setStock(productoBodega.getStock() + dto.getStock());
        }

        return productoBodegaRepository.save(productoBodega);
    }

    // ✅ Nuevo: Obtener stock total de un producto en todas las bodegas
    public int obtenerStockTotalPorProducto(Long productoId) {
        return productoBodegaRepository.findByProductoId(productoId)
                .stream()
                .mapToInt(ProductoBodega::getStock)
                .sum();
    }

    // ✅ Eliminar un producto de una bodega
    public void eliminarProductoDeBodega(Long productoBodegaId) {
        productoBodegaRepository.deleteById(productoBodegaId);
    }
}

