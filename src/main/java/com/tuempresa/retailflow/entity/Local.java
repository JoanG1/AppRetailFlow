package com.tuempresa.retailflow.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "local")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    // Relación con ProductoLocal
    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductoLocal> productosEnLocal;


}
