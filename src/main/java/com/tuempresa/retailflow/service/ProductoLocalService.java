package com.tuempresa.retailflow.service;


import com.tuempresa.retailflow.dto.CrearLocalDTO;
import com.tuempresa.retailflow.dto.EditarProductoLocalDTO;
import com.tuempresa.retailflow.dto.ProductoLocalDTO;
import com.tuempresa.retailflow.entity.Local;
import com.tuempresa.retailflow.entity.ProductoLocal;
import com.tuempresa.retailflow.repository.LocalRepository;
import com.tuempresa.retailflow.repository.ProductoLocalRepository;
import com.tuempresa.retailflow.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductoLocalService {

    private final ProductoLocalRepository productoLocalRepository;
    private final LocalRepository localRepository;
    private final ProductoRepository productoRepository;

    // ✅ Obtener productos del local
    public List<ProductoLocalDTO> obtenerProductosPorLocal(Long localId) {
        List<ProductoLocal> productos = productoLocalRepository.findByLocalId(localId);
        return productos.stream().map(pl -> new ProductoLocalDTO(
                pl.getId(),
                pl.getProducto().getId(),
                pl.getLocal().getId(),
                pl.getStock()
        )).collect(Collectors.toList());
    }

    // ✅ Nuevo: Obtener stock total de un producto en todas las bodegas
    public int obtenerStockTotalPorProducto(Long productoId) {
        return productoLocalRepository.findById(productoId)
                .stream()
                .mapToInt(ProductoLocal::getStock)
                .sum();
    }

    // ✅ Eliminar un producto de una bodega
    public void eliminarProductoDeLocal(Long productoBodegaId) {
        productoLocalRepository.deleteById(productoBodegaId);
    }

    // Crear un producto local asignandolo a un local
    public void crearProductoLocal(@RequestBody ProductoLocalDTO dto) {

        ProductoLocal productoLocal = new ProductoLocal();
        productoLocal.setProducto(productoRepository.findById(dto.getProductoId()).get());
        productoLocal.setLocal(localRepository.findById(dto.getLocalId()).get());
        productoLocal.setStock(dto.getStock());
        productoLocalRepository.save(productoLocal);

    }

    //Actualizar el producto de local
    public ProductoLocal actualizarProductoLocal(Long id, EditarProductoLocalDTO dto) {
        ProductoLocal productoLocal = productoLocalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bodega no encontrada"));

        productoLocal.setStock(dto.getStock());

        return productoLocalRepository.save(productoLocal);
    }

}
