package com.uade.tpejemplo.service.impl;

import com.uade.tpejemplo.dto.request.CreditoRequest;
import com.uade.tpejemplo.dto.response.CreditoResponse;
import com.uade.tpejemplo.dto.response.CuotaResponse;
import com.uade.tpejemplo.exception.BusinessException;
import com.uade.tpejemplo.exception.ResourceNotFoundException;
import com.uade.tpejemplo.model.Cliente;
import com.uade.tpejemplo.model.Credito;
import com.uade.tpejemplo.model.Cuota;
import com.uade.tpejemplo.repository.ClienteRepository;
import com.uade.tpejemplo.repository.CobranzaRepository;
import com.uade.tpejemplo.repository.CreditoRepository;
import com.uade.tpejemplo.repository.CuotaRepository;
import com.uade.tpejemplo.service.CreditoService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditoServiceImpl implements CreditoService {

    private final CreditoRepository creditoRepository;
    private final ClienteRepository clienteRepository;
    private final CuotaRepository cuotaRepository;
    private final CobranzaRepository cobranzaRepository;

    @Override
    @Transactional
    public CreditoResponse crear(CreditoRequest request) {
        Cliente cliente = buscarCliente(request.getDniCliente());

        Credito credito = creditoRepository.save(nuevoCredito(request, cliente));
        List<Cuota> cuotas = cuotaRepository.saveAll(credito.generarPlanDeCuotas());

        return toResponse(credito, cuotas);
    }

    @Override
    public CreditoResponse buscarPorId(Long id) {
        Credito credito = buscarCredito(id);
        return toResponse(credito, cuotaRepository.findByIdIdCredito(id));
    }

    @Override
    public List<CreditoResponse> listarPorCliente(String dniCliente) {
        if (!clienteRepository.existsByDni(dniCliente)) {
            throw new ResourceNotFoundException("Cliente", "DNI", dniCliente);
        }
        return creditoRepository.findByClienteDni(dniCliente).stream()
            .map(c -> toResponse(c, cuotaRepository.findByIdIdCredito(c.getId())))
            .toList();
    }

    @Override
    public void eliminarCredito(Long id) {
        creditoRepository.deleteById(id);
    }

    @Override
    public void anularCredito(Long id) {
        Credito credito = buscarCredito(id);

        if (cobranzaRepository.existsByCuotaIdIdCredito(id)) {
            throw new BusinessException(
                "No se puede anular el crédito " + id + " porque tiene cobranzas registradas."
            );
        }

        credito.setAnulado(true);
        creditoRepository.save(credito);
    }

    private Cliente buscarCliente(String dni) {
        return clienteRepository.findByDni(dni)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", "DNI", dni));
    }

    private Credito buscarCredito(Long id) {
        return creditoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Crédito", "id", id));
    }

    private Credito nuevoCredito(CreditoRequest request, Cliente cliente) {
        return new Credito(
            null,
            cliente,
            request.getDeudaOriginal(),
            request.getFecha(),
            request.getImporteCuota(),
            request.getCantidadCuotas(),
            null,
            false
        );
    }

    private CreditoResponse toResponse(Credito credito, List<Cuota> cuotas) {
        List<CuotaResponse> cuotasResponse = cuotas.stream()
            .map(cuota -> CuotaResponse.desde(cuota, estaPagada(cuota)))
            .toList();

        return CreditoResponse.desde(credito, cuotasResponse);
    }

    private boolean estaPagada(Cuota cuota) {
        return cobranzaRepository.existsByCuotaIdIdCreditoAndCuotaIdIdCuota(
            cuota.getId().getIdCredito(),
            cuota.getId().getIdCuota()
        );
    }
}
