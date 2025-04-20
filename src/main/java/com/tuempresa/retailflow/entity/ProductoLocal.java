package com.tuempresa.retailflow.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto_local")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoLocal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonBackReference
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false)
    @JsonBackReference
    private Local local;

    @Column(nullable = false)
    private Integer stock;
}
