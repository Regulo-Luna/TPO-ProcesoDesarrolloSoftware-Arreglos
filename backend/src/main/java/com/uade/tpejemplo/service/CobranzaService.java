package com.uade.tpejemplo.service;

import com.uade.tpejemplo.dto.request.CobranzaRequest;
import com.uade.tpejemplo.dto.response.CobranzaResponse;
import com.uade.tpejemplo.exception.BusinessException;
import com.uade.tpejemplo.exception.ResourceNotFoundException;
import com.uade.tpejemplo.model.Cobranza;
import com.uade.tpejemplo.model.Cuota;
import com.uade.tpejemplo.model.CuotaId;
import com.uade.tpejemplo.repository.CobranzaRepository;
import com.uade.tpejemplo.repository.CuotaRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CobranzaService {

    private final CobranzaRepository cobranzaRepository;
    private final CuotaRepository cuotaRepository;

    @Transactional
    public CobranzaResponse registrar(CobranzaRequest request) {
        CuotaId cuotaId = new CuotaId(request.getIdCredito(), request.getIdCuota());

        Cuota cuota = cuotaRepository.findById(cuotaId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Cuota", "idCredito/idCuota", request.getIdCredito() + "/" + request.getIdCuota()
            ));

        if (cuota.estaPagada()) {
            throw new BusinessException(
                "La cuota " + request.getIdCuota() + " del crédito " + request.getIdCredito() + " ya fue pagada"
            );
        }

        Cobranza cobranza = Cobranza.registrar(cuota, request.getImporte());
        cobranzaRepository.save(cobranza);
        return CobranzaResponse.desde(cobranza);
    }

    public List<CobranzaResponse> listarPorCredito(Long idCredito) {
        return cobranzaRepository.buscarPorCredito(idCredito).stream()
            .map(CobranzaResponse::desde)
            .toList();
    }

    public void anularCobranza(Long id) {
        Cobranza cobranza = cobranzaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cobranza", "id", id));

        // Regla de negocio: solo se pueden anular cobranzas del dia de hoy
        if (!cobranza.getFechaCobranza().isEqual(LocalDate.now())) {
            throw new BusinessException("Solo se pueden anular cobranzas del día de hoy.");
        }

        cobranza.anular();
        cobranzaRepository.save(cobranza);
    }
}
