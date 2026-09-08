package com.uade.tpejemplo.service;

import com.uade.tpejemplo.dto.request.ClienteRequest;
import com.uade.tpejemplo.dto.response.ClienteResponse;
import com.uade.tpejemplo.exception.BusinessException;
import com.uade.tpejemplo.exception.ResourceNotFoundException;
import com.uade.tpejemplo.model.Cliente;
import com.uade.tpejemplo.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponse crear(ClienteRequest request) {
        if (clienteRepository.existsById(request.getDni())) {
            throw new BusinessException("Ya existe un cliente con DNI: " + request.getDni());
        }
        Cliente cliente = Cliente.nuevo(request.getDni(), request.getNombre());
        clienteRepository.save(cliente);
        return ClienteResponse.desde(cliente);
    }

    public ClienteResponse buscarPorDni(String dni) {
        Cliente cliente = clienteRepository.findById(dni)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", "DNI", dni));
        return ClienteResponse.desde(cliente);
    }

    public List<ClienteResponse> listarTodos() {
        return clienteRepository.findAll().stream()
            .map(ClienteResponse::desde)
            .toList();
    }
}
