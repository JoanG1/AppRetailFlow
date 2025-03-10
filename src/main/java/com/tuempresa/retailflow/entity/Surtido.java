package com.tuempresa.retailflow.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "surtidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Surtido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "surtido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SurtidoProducto> productosSurtidos;

    @Column(nullable = false)
    private LocalDateTime fechaSurtido;
}