package com.tuempresa.retailflow.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "secciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    // Relación con Bodega: Cada sección pertenece a una única bodega
    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = false) // Clave foránea en la tabla 'secciones'
    @JsonBackReference
    private Bodega bodega;

    // Relación con ProductoBodega
    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductoBodega> productosEnSeccion;
}