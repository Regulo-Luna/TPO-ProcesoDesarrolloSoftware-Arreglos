package com.uade.tpejemplo.service;

import com.uade.tpejemplo.dto.request.MetaCobranzaRequest;
import com.uade.tpejemplo.exception.ResourceNotFoundException;
import com.uade.tpejemplo.model.MetaCobranza;
import com.uade.tpejemplo.repository.MetaCobranzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetaCobranzaService {

    private final MetaCobranzaRepository metaCobranzaRepository;

    public List<MetaCobranzaRequest> obtenerTodas() {
        return metaCobranzaRepository.findAll().stream()
            .map(MetaCobranzaRequest::desde)
            .toList();
    }

    @Transactional
    public MetaCobranzaRequest guardarMeta(MetaCobranzaRequest metaRequest) {
        MetaCobranza meta = MetaCobranza.nueva(metaRequest.getMes(), metaRequest.getMontoObjetivo());
        return MetaCobranzaRequest.desde(metaCobranzaRepository.save(meta));
    }

    @Transactional
    public MetaCobranzaRequest actualizarMeta(Long id, MetaCobranzaRequest metaRequest) {
        MetaCobranza meta = buscarMeta(id);

        meta.actualizar(metaRequest.getMes(), metaRequest.getMontoObjetivo());

        return MetaCobranzaRequest.desde(metaCobranzaRepository.save(meta));
    }

    @Transactional
    public void eliminarMeta(Long id) {
        metaCobranzaRepository.delete(buscarMeta(id));
    }

    private MetaCobranza buscarMeta(Long id) {
        return metaCobranzaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Meta de cobranza", "id", id));
    }
}
