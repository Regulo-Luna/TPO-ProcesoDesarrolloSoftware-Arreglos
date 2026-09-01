package com.uade.tpejemplo.service.impl;

import com.uade.tpejemplo.service.MetaCobranzaService;
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
public class MetaCobranzaServiceImpl implements MetaCobranzaService {

    private final MetaCobranzaRepository metaCobranzaRepository;

    @Override
    public List<MetaCobranzaRequest> obtenerTodas() {
        return metaCobranzaRepository.findAll().stream()
            .map(MetaCobranzaRequest::desde)
            .toList();
    }

    @Override
    @Transactional
    public MetaCobranzaRequest guardarMeta(MetaCobranzaRequest metaRequest) {
        MetaCobranza meta = new MetaCobranza();
        meta.setMes(metaRequest.getMes());
        meta.setMontoObjetivo(metaRequest.getMontoObjetivo());
        return MetaCobranzaRequest.desde(metaCobranzaRepository.save(meta));
    }

    @Override
    @Transactional
    public MetaCobranzaRequest actualizarMeta(Long id, MetaCobranzaRequest metaRequest) {
        MetaCobranza meta = buscarMeta(id);

        meta.setMes(metaRequest.getMes());
        meta.setMontoObjetivo(metaRequest.getMontoObjetivo());

        return MetaCobranzaRequest.desde(metaCobranzaRepository.save(meta));
    }

    @Override
    @Transactional
    public void eliminarMeta(Long id) {
        metaCobranzaRepository.delete(buscarMeta(id));
    }

    private MetaCobranza buscarMeta(Long id) {
        return metaCobranzaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Meta de cobranza", "id", id));
    }
}
