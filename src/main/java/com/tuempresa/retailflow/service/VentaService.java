package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.*;
import com.tuempresa.retailflow.entity.*;
import com.tuempresa.retailflow.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final VentaDetalleRepository ventaDetalleRepository;
    private final ProductoLocalRepository productoLocalRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final LocalRepository localRepository;

    public VentaService(VentaRepository ventaRepository,
                        VentaDetalleRepository ventaDetalleRepository,
                        ProductoLocalRepository productoLocalRepository,
                        UsuarioRepository usuarioRepository,
                        ProductoRepository productoRepository,
                        LocalRepository localRepository) {
        this.ventaRepository = ventaRepository;
        this.ventaDetalleRepository = ventaDetalleRepository;
        this.productoLocalRepository = productoLocalRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.localRepository = localRepository;
    }

    @Transactional
    public VentaDTO crearVenta(CrearVentaDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Local local = localRepository.findById(dto.getLocalId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local no encontrado"));

        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setUsuario(usuario);
        venta.setTotal(0.0);
        venta = ventaRepository.save(venta);

        double total = 0;
        List<VentaDetalleDTO> detalleDTOs = new ArrayList<>();

        for (CrearVentaDetalleDTO detalleDTO : dto.getProductos()) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Producto no encontrado con ID: " + detalleDTO.getProductoId()));

            ProductoLocal productoLocal = productoLocalRepository
                    .findByProductoIdAndLocalId(producto.getId(), local.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "El producto '" + producto.getNombre() + "' no está disponible en el local '" + local.getNombre() + "'"));

            if (productoLocal.getStock() < detalleDTO.getCantidad()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Stock insuficiente para el producto '" + producto.getNombre()
                                + "' en el local '" + local.getNombre() + "'");
            }

            // Descontar stock
            productoLocal.setStock(productoLocal.getStock() - detalleDTO.getCantidad());
            productoLocalRepository.save(productoLocal);

            double precioUnitario = producto.getPrecio(); // ✅ AQUÍ usamos el precio correcto

            VentaDetalle detalle = new VentaDetalle();
            detalle.setVenta(venta);
            detalle.setProductoLocal(productoLocal);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(precioUnitario);
            ventaDetalleRepository.save(detalle);

            double subtotal = detalleDTO.getCantidad() * precioUnitario;
            total += subtotal;

            VentaDetalleDTO d = new VentaDetalleDTO();
            d.setNombreProducto(producto.getNombre());
            d.setNombreLocal(local.getNombre());
            d.setCantidad(detalleDTO.getCantidad());
            d.setPrecioUnitario(precioUnitario);
            d.setSubtotal(subtotal);
            detalleDTOs.add(d);
        }

        venta.setTotal(total);
        ventaRepository.save(venta);

        VentaDTO respuesta = new VentaDTO();
        respuesta.setId(venta.getId());
        respuesta.setFecha(venta.getFecha());
        respuesta.setNombreUsuario(usuario.getUsername());
        respuesta.setTotal(total);
        respuesta.setDetalles(detalleDTOs);

        return respuesta;
    }

    public List<VentaHistorialDTO> obtenerHistorialDeVentas() {
        List<Venta> ventas = ventaRepository.findAll();

        List<VentaHistorialDTO> historial = new ArrayList<>();

        for (Venta venta : ventas) {
            VentaHistorialDTO dto = new VentaHistorialDTO();
            dto.setNombreUsuario(venta.getUsuario().getUsername());
            dto.setFecha(venta.getFecha());
            dto.setTotal(venta.getTotal());

            List<VentaDetalle> detalles = ventaDetalleRepository.findByVentaId(venta.getId());

            List<VentaProductoHistorialDTO> productos = new ArrayList<>();
            for (VentaDetalle detalle : detalles) {
                VentaProductoHistorialDTO prod = new VentaProductoHistorialDTO();
                prod.setNombreProducto(detalle.getProductoLocal().getProducto().getNombre());
                prod.setCantidad(detalle.getCantidad());
                prod.setPrecioUnitario(detalle.getPrecioUnitario());
                productos.add(prod);
            }

            dto.setProductos(productos);
            historial.add(dto);
        }

        return historial;
    }


}
