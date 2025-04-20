package com.tuempresa.retailflow.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "surtido_productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurtidoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "surtido_id", nullable = false)
    @JsonBackReference
    private Surtido surtido;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonBackReference
    private Producto producto;

    @Column(nullable = false)
    private int cantidad;  // Cantidad de este producto en el surtido
}