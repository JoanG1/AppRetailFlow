package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.BodegaDTO;
import com.tuempresa.retailflow.dto.CrearBodegaDTO;
import com.tuempresa.retailflow.dto.SeccionDTO;
import com.tuempresa.retailflow.entity.Bodega;
import com.tuempresa.retailflow.entity.Seccion;
import com.tuempresa.retailflow.repository.BodegaRepository;
import com.tuempresa.retailflow.repository.SeccionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BodegaService {

    private final BodegaRepository bodegaRepository;
    private final SeccionRepository seccionRepository;

    // ✅ Obtener todas las bodegas con sus secciones
    public List<BodegaDTO> obtenerTodasLasBodegas() {
        return bodegaRepository.findAll().stream()
                .map(this::convertirABodegaDTO)
                .collect(Collectors.toList());
    }

    // ✅ Obtener una bodega por ID
    public BodegaDTO obtenerBodegaPorId(Long id) {
        Bodega bodega = bodegaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bodega no encontrada"));
        return convertirABodegaDTO(bodega);
    }

    // ✅ Crear una nueva bodega
    public BodegaDTO crearBodega(CrearBodegaDTO dto) {
        Bodega bodega = new Bodega();
        bodega.setNombre(dto.getNombre());

        Bodega bodegaGuardada = bodegaRepository.save(bodega);

        // Si el usuario no envió secciones, agregamos una por defecto
        if (dto.getSecciones() == null || dto.getSecciones().isEmpty()) {
            Seccion seccionPorDefecto = new Seccion();
            seccionPorDefecto.setNombre("Sección Principal");
            seccionPorDefecto.setBodega(bodegaGuardada);
            seccionRepository.save(seccionPorDefecto);
        } else {
            // Crear las secciones enviadas por el usuario
            for (String nombreSeccion : dto.getSecciones()) {
                Seccion seccion = new Seccion();
                seccion.setNombre(nombreSeccion);
                seccion.setBodega(bodegaGuardada);
                seccionRepository.save(seccion);
            }
        }

        return convertirABodegaDTO(bodegaGuardada);
    }

    // ✅ Actualizar bodega
    public BodegaDTO actualizarBodega(Long id, BodegaDTO dto) {
        Bodega bodega = bodegaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bodega no encontrada"));

        bodega.setNombre(dto.getNombre());

        return convertirABodegaDTO(bodegaRepository.save(bodega));
    }
    public void eliminarBodega(Long id) {
        // 1️⃣ Verificar si la bodega existe
        Bodega bodega = bodegaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bodega no encontrada"));

        // 2️⃣ Verificar si la bodega tiene secciones con productos
        List<Seccion> secciones = seccionRepository.findByBodegaId(id);
        for (Seccion seccion : secciones) {
            if (!seccion.getProductosEnSeccion().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No se puede eliminar la bodega porque tiene productos asignados en sus secciones");
            }
        }

        // 3️⃣ Eliminar secciones antes de eliminar la bodega
        seccionRepository.deleteAll(secciones);

        // 4️⃣ Finalmente, eliminar la bodega
        bodegaRepository.deleteById(id);
    }

    // 🔄 Conversión de Entidad a DTO
    private BodegaDTO convertirABodegaDTO(Bodega bodega) {
        BodegaDTO dto = new BodegaDTO();
        dto.setId(bodega.getId());
        dto.setNombre(bodega.getNombre());

        List<SeccionDTO> secciones = seccionRepository.findByBodegaId(bodega.getId()).stream()
                .map(seccion -> {
                    SeccionDTO seccionDTO = new SeccionDTO();
                    seccionDTO.setId(seccion.getId());
                    seccionDTO.setNombre(seccion.getNombre());
                    seccionDTO.setBodegaId(bodega.getId());
                    return seccionDTO;
                }).collect(Collectors.toList());

        dto.setSecciones(secciones);
        return dto;
    }
}
