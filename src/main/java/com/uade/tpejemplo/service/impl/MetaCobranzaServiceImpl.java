package com.uade.tpejemplo.service.impl;

import com.uade.tpejemplo.service.MetaCobranzaService;
import com.uade.tpejemplo.dto.request.MetaCobranzaRequest;
import com.uade.tpejemplo.model.MetaCobranza;
import com.uade.tpejemplo.repository.MetaCobranzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetaCobranzaServiceImpl implements MetaCobranzaService {

    private final MetaCobranzaRepository repository;

    @Override
    public List<MetaCobranzaRequest> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MetaCobranzaRequest guardarMeta(MetaCobranzaRequest metaRequest) {
        MetaCobranza meta = new MetaCobranza();
        meta.setMes(metaRequest.getMes());
        meta.setMontoObjetivo(metaRequest.getMontoObjetivo());
        return convertirADTO(repository.save(meta));
    }

    @Override
    @Transactional
    public MetaCobranzaRequest actualizarMeta(Long id, MetaCobranzaRequest metaRequest) {
        MetaCobranza meta = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meta no encontrada con ID: " + id));
        
        meta.setMes(metaRequest.getMes());
        meta.setMontoObjetivo(metaRequest.getMontoObjetivo());
        
        return convertirADTO(repository.save(meta));
    }

    @Override
    @Transactional
    public void eliminarMeta(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, meta no encontrada");
        }
        repository.deleteById(id);
    }

    private MetaCobranzaRequest convertirADTO(MetaCobranza meta) {
        return new MetaCobranzaRequest(meta.getId(), meta.getMes(), meta.getMontoObjetivo());
    }
}