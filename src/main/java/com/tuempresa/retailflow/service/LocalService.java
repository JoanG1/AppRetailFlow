package com.tuempresa.retailflow.service;


import com.tuempresa.retailflow.dto.*;
import com.tuempresa.retailflow.entity.Bodega;
import com.tuempresa.retailflow.entity.Local;
import com.tuempresa.retailflow.repository.LocalRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;

    // ✅ Obtener todas las bodegas con sus secciones
    public List<Local> obtenerTodasLosLocales() {
        return localRepository.findAll();
    }

    //
    public List<Local> obtenerLocalPorId(Long id) {
        return localRepository.findByid(id);
    }

    public Local crearLocal(CrearLocalDTO dto) {

        Local local = new Local();
        local.setNombre(dto.getNombre());
        localRepository.save(local);
        return local;
    }

    public void eliminarLocal(Long id) {
        // 1️⃣ Verificar si la bodega existe
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "local no encontrado"));

        // 4️⃣ Finalmente, eliminar la bodega
        localRepository.deleteById(id);
    }

    // ✅ Actualizar local
    public Local actualizarLocal(Long id, CrearLocalDTO dto) {
        Local bodega = localRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bodega no encontrada"));

        bodega.setNombre(dto.getNombre());

        return localRepository.save(bodega);
    }

    // ✅ Contar locales
    public long contarLocales() {
        return localRepository.count();
    }





}
