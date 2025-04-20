package com.tuempresa.retailflow.repository;

import com.tuempresa.retailflow.entity.ProductoBodega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoBodegaRepository extends JpaRepository<ProductoBodega, Long> {

    // Obtener todos los productos de una bodega
    List<ProductoBodega> findByBodegaId(Long bodegaId);

    // Obtener todos los productos de una sección dentro de una bodega
    List<ProductoBodega> findByBodegaIdAndSeccionId(Long bodegaId, Long seccionId);

    // Obtener productos por nombre dentro de una bodega
    List<ProductoBodega> findByBodegaIdAndProductoNombreContainingIgnoreCase(Long bodegaId, String nombre);

    // ✅ Método para buscar por producto, bodega y sección (para evitar duplicados)
    Optional<ProductoBodega> findByProductoIdAndBodegaIdAndSeccionId(Long productoId, Long bodegaId, Long seccionId);

    // ✅ Método para buscar todas las entradas de un producto en cualquier bodega
    @Override
    Optional<ProductoBodega> findById(Long Id);

    ProductoBodega findByProductoId(Long productoId);

    // Obtener todos los productos de una sección dentro de una bodega
    List<ProductoBodega> findBySeccionId(Long seccionId);

}