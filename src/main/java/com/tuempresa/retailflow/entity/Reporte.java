package com.tuempresa.retailflow.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo; // "inventario" o "surtidos"

    @Column(nullable = false)
    private LocalDateTime fechaGeneracion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido; // JSON, CSV o cualquier otro formato de reporte
}
