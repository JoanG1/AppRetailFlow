package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.SurtidoDTO;
import com.tuempresa.retailflow.entity.Producto;
import com.tuempresa.retailflow.entity.Surtido;
import com.tuempresa.retailflow.entity.SurtidoProducto;
import com.tuempresa.retailflow.repository.ProductoRepository;
import com.tuempresa.retailflow.repository.SurtidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurtidoService {

    private final SurtidoRepository surtidoRepository;
    private final ProductoRepository productoRepository;

    public SurtidoService(SurtidoRepository surtidoRepository, ProductoRepository productoRepository) {
        this.surtidoRepository = surtidoRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Surtido registrarSurtido(SurtidoDTO dto) {

        Surtido surtido = new Surtido();
        surtido.setFechaSurtido(dto.getFechaSurtido());

        List<SurtidoProducto> productosSurtidos = dto.getProductos().stream()
                .map(item -> {
                    Producto producto = productoRepository.findById(item.getId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

                    if (producto.getStock() < item.getCantidad()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente para producto " + producto.getNombre());
                    }

                    producto.setStock(producto.getStock() - item.getCantidad());
                    productoRepository.save(producto);

                    return new SurtidoProducto(null, surtido, producto, item.getCantidad());
                })
                .collect(Collectors.toList());

        surtido.setProductosSurtidos(productosSurtidos);

        return surtidoRepository.save(surtido);
    }

    public List<Surtido> obtenerHistorialSurtidos() {
        return surtidoRepository.findAll();
    }
}
