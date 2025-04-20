package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.BodegaDTO;
import com.tuempresa.retailflow.dto.CrearBodegaDTO;
import com.tuempresa.retailflow.entity.Bodega;
import com.tuempresa.retailflow.entity.Seccion;
import com.tuempresa.retailflow.repository.BodegaRepository;
import com.tuempresa.retailflow.repository.SeccionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BodegaService {

    private final BodegaRepository bodegaRepository;
    private final SeccionRepository seccionRepository;

    // ✅ Obtener todas las bodegas con sus secciones
    public List<Bodega> obtenerTodasLasBodegas() {
        return bodegaRepository.findAll();
    }

    // ✅ Obtener una bodega por ID
    public Bodega obtenerBodegaPorId(Long id) {
        Bodega bodega = bodegaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bodega no encontrada"));
        return bodega;
    }

    @Transactional
    public Bodega crearBodega(CrearBodegaDTO dto) {
        Bodega bodega = new Bodega();
        bodega.setNombre(dto.getNombre());

        List<Seccion> secciones = new ArrayList<>();

        for (String nombre : dto.getSecciones()) {
            if (nombre == null || nombre.isBlank()) {
                continue;
            }

            String nombreLimpio = nombre.trim();
            Optional<Seccion> seccionExistente = seccionRepository.findByNombre(nombreLimpio);

            if (seccionExistente.isPresent()) {
                Seccion existente = seccionExistente.get();
                if (existente.getBodega() != null) {
                    System.out.println("La sección con nombre '" + nombreLimpio + "' ya está asignada a otra bodega y no será añadida.");
                    // O puedes usar un logger:
                    // log.warn("La sección '{}' ya pertenece a la bodega '{}'", nombreLimpio, existente.getBodega().getNombre());
                    continue;
                } else {
                    existente.setBodega(bodega);
                    secciones.add(existente);
                }
            } else {
                Seccion nuevaSeccion = new Seccion();
                nuevaSeccion.setNombre(nombreLimpio);
                nuevaSeccion.setBodega(bodega);
                secciones.add(nuevaSeccion);
            }
        }

        bodega.setSecciones(secciones);
        return bodegaRepository.save(bodega);
    }



    @Transactional
    public Bodega crearBodegaVacia(BodegaDTO dto) {
        Bodega bodega = new Bodega();
        bodega.setNombre(dto.getNombre());

        Bodega bodegaGuardada =bodegaRepository.save(bodega);

        Seccion seccionPorDefecto = new Seccion();
        seccionPorDefecto.setNombre("SECCION PRINCIPAL "+ dto.getNombre() );
        seccionPorDefecto.setBodega(bodegaGuardada);
        seccionRepository.save(seccionPorDefecto);

        return bodegaRepository.getBodegaById(bodegaGuardada.getId()).get();
    }



    // ✅ Actualizar bodega
    public Bodega actualizarBodega(Long id, BodegaDTO dto) {
        Bodega bodega = bodegaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bodega no encontrada"));

        bodega.setNombre(dto.getNombre());

        return bodegaRepository.save(bodega);
    }

    //eliminar bodega
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


    // ✅ Contar producto
    public long contarBodegas() {
        return bodegaRepository.count();
    }



}
