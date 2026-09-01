package com.uade.tpejemplo.service;

import com.uade.tpejemplo.dto.request.MetaCobranzaRequest;
import java.util.List;

public interface MetaCobranzaService {
    List<MetaCobranzaRequest> obtenerTodas();
    MetaCobranzaRequest guardarMeta(MetaCobranzaRequest metaRequest);
    MetaCobranzaRequest actualizarMeta(Long id, MetaCobranzaRequest metaRequest);
    void eliminarMeta(Long id);
}