package com.uade.tpejemplo.controller;

import com.uade.tpejemplo.dto.request.MetaCobranzaRequest; // Ajusta según tu estructura
import com.uade.tpejemplo.service.MetaCobranzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class MetaCobranzaController {

    private final MetaCobranzaService metaService;

    @GetMapping
    public ResponseEntity<List<MetaCobranzaRequest>> listarMetas() {
        return ResponseEntity.ok(metaService.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<MetaCobranzaRequest> crearMeta(@Valid @RequestBody MetaCobranzaRequest metaRequest) {
        return ResponseEntity.ok(metaService.guardarMeta(metaRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetaCobranzaRequest> actualizarMeta(@PathVariable Long id, @Valid @RequestBody MetaCobranzaRequest metaRequest) {
        return ResponseEntity.ok(metaService.actualizarMeta(id, metaRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMeta(@PathVariable Long id) {
        metaService.eliminarMeta(id);
        return ResponseEntity.noContent().build();
    }
}