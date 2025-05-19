package com.tuempresa.retailflow.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "venta_detalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;

    private double precioUnitario;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false) // Este es ID de ProductoLocal realmente
    private ProductoLocal productoLocal;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;
}

