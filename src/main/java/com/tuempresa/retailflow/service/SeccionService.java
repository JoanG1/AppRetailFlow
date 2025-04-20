package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.SeccionDTO;
import com.tuempresa.retailflow.dto.ProductoBodegaDTO;
import com.tuempresa.retailflow.entity.Bodega;
import com.tuempresa.retailflow.entity.Seccion;
import com.tuempresa.retailflow.entity.ProductoBodega;
import com.tuempresa.retailflow.repository.BodegaRepository;
import com.tuempresa.retailflow.repository.SeccionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SeccionService {

    private final SeccionRepository seccionRepository;
    private final BodegaRepository bodegaRepository;

    // ✅ Obtener todas las secciones
    public List<Seccion> obtenerTodasLasSecciones() {
        return seccionRepository.findAll();
    }

    // ✅ Obtener una sección por ID
    public Seccion obtenerSeccionPorId(Long id) {
        Seccion seccion = seccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sección no encontrada"));
        return seccion;
    }

    public Seccion crearSeccion(SeccionDTO dto) {
        if (dto.getBodegaId() == null || dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre y Bodega son obligatorios");
        }

        Bodega bodega = bodegaRepository.findById(dto.getBodegaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bodega no encontrada"));

        // ❌ Validación: Evitar duplicidad de nombres en la misma bodega
        boolean existeSeccion = seccionRepository.existsByNombreAndBodega(dto.getNombre(), bodega);
        if (existeSeccion) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una sección con este nombre en la bodega");
        }

        Seccion seccion = new Seccion();
        seccion.setNombre(dto.getNombre());
        seccion.setBodega(bodega);

        Seccion seccionGuardada = seccionRepository.save(seccion);
        return seccionGuardada;
    }

    // ✅ Actualizar una sección con validaciones
    public Seccion actualizarSeccion(Long id, SeccionDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la sección no puede estar vacío.");
        }

        if (dto.getBodegaId() == null) {
            throw new IllegalArgumentException("El ID de la bodega es obligatorio.");
        }

        Seccion seccion = seccionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bodega no encontrada"));

        Bodega bodega = bodegaRepository.findById(dto.getBodegaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bodega no encontrada"));

        // 🔍 Validación: verificar que no exista otra sección con el mismo nombre en la misma bodega
        boolean existeSeccion = seccionRepository.existsByNombreAndBodega(dto.getNombre(), bodega);
        if (existeSeccion && !seccion.getNombre().equals(dto.getNombre())) {
            throw new IllegalStateException("Ya existe una sección con este nombre en la bodega.");
        }

        seccion.setNombre(dto.getNombre());
        seccion.setBodega(bodega);

        return seccionRepository.save(seccion);
    }

    // ✅ Eliminar una sección
    public void eliminarSeccion(Long id) {
        seccionRepository.deleteById(id);
    }

    // obtener secciones por bodega

    public List<Seccion> obtenerSeccionPorBodegaId(Long bodegaId) {
        return seccionRepository.findByBodegaId(bodegaId);
    }

}

