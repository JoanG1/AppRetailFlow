package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.ProductoDTO;
import com.tuempresa.retailflow.dto.SurtidoDTO;
import com.tuempresa.retailflow.dto.SurtidoProductoDTO;
import com.tuempresa.retailflow.entity.*;
import com.tuempresa.retailflow.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SurtidoService {

    private final SurtidoRepository surtidoRepository;
    private final ProductoBodegaRepository productoBodegaRepository;
    private final ProductoRepository productoRepository;
    private final ProductoLocalRepository productoLocalRepository;
    private final LocalRepository localRepository;


    public List<Surtido> obtenerHistorialSurtidos() {
        return surtidoRepository.findAll();
    }

    //Obtener solo los primeros diez productos
    public List<Surtido> obtenerTopSurtidos() {
        Pageable topDiez = PageRequest.of(0, 10);
        return surtidoRepository.findAll(topDiez).getContent();
    }



    @Transactional
    public Surtido crearSurtido(SurtidoDTO surtidoDTO, Long localId) {
        Surtido surtido = new Surtido();
        surtido.setFechaSurtido(surtidoDTO.getFechaSurtido());

        // Obtener el Local una sola vez
        Local local = localRepository.findById(localId)
                .orElseThrow(() -> new RuntimeException("Local no encontrado con ID: " + localId));

        List<SurtidoProducto> productosSurtidos = new ArrayList<>();

        for (SurtidoProductoDTO productoDTO : surtidoDTO.getProductos()) {
            Long productoId = productoDTO.getProductoId().longValue();
            int cantidad = productoDTO.getCantidad();

            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));

            ProductoBodega productoBodega = productoBodegaRepository.findByProductoId(productoId);
            if (productoBodega == null) {
                throw new RuntimeException("Producto no encontrado en bodega con ID: " + productoId);
            }

            if (productoBodega.getStock() < cantidad) {
                throw new RuntimeException("Stock insuficiente en bodega para producto ID: " + productoId);
            }

            // Descontar de bodega
            productoBodega.setStock(productoBodega.getStock() - cantidad);
            productoBodegaRepository.save(productoBodega);

            // Buscar si ya existe el producto en el local
            Optional<ProductoLocal> optionalProductoLocal =
                    productoLocalRepository.findByProductoIdAndLocalId(productoId, localId);

            ProductoLocal productoLocal;
            if (optionalProductoLocal.isPresent()) {
                productoLocal = optionalProductoLocal.get();
                productoLocal.setStock(productoLocal.getStock() + cantidad);
            } else {
                productoLocal = new ProductoLocal();
                productoLocal.setProducto(producto);
                productoLocal.setLocal(local);
                productoLocal.setStock(cantidad);
            }

            productoLocalRepository.save(productoLocal);

            // Crear y añadir el surtidoProducto
            SurtidoProducto surtidoProducto = new SurtidoProducto();
            surtidoProducto.setProducto(producto);
            surtidoProducto.setCantidad(cantidad);
            surtidoProducto.setSurtido(surtido);

            productosSurtidos.add(surtidoProducto);
        }

        surtido.setProductosSurtidos(productosSurtidos);
        return surtidoRepository.save(surtido);
    }


    // ✅ Contar producto
    public long contarSurtidos() {
        return surtidoRepository.count();
    }


}
