package com.tuempresa.retailflow.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "producto_bodega")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoBodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonBackReference
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = false)
    @JsonBackReference
    private Bodega bodega;

    @ManyToOne
    @JoinColumn(name = "seccion_id", nullable = false)
    @JsonBackReference
    private Seccion seccion;

    @Column(nullable = false)
    private Integer stock;
}
