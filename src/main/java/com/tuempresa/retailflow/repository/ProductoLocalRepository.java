package com.tuempresa.retailflow.repository;

import com.tuempresa.retailflow.entity.ProductoBodega;
import com.tuempresa.retailflow.entity.ProductoLocal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoLocalRepository extends JpaRepository<ProductoLocal, Long> {

    // Obtener todos los productos del local
    List<ProductoLocal> findByLocalId(Long localId);

    // Obtener productos por nombre dentro de una bodega
    List<ProductoLocal> findByLocalIdAndProductoNombreContainingIgnoreCase(Long bodegaId, String nombre);

    // ✅ Método para buscar todas las entradas de un producto en cualquier bodega
    @Override
    Optional<ProductoLocal> findById(Long Id);

    ProductoLocal findByProductoId(Long productoId);

    Optional<ProductoLocal> findByProductoIdAndLocalId(Long productoId, Long localId);

}
