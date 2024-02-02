package io.github.jonasnascc.SeuSindico_API.controller.Contrato;

import io.github.jonasnascc.SeuSindico_API.dto.Contrato.ContratoDTO;
import io.github.jonasnascc.SeuSindico_API.service.Contrato.ContratoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContratoController {
    private final ContratoService service;

    @PostMapping("users/{proprietarioId}/imoveis/{imovelId}/contratos/ocupante/{ocupanteId}")
    public ResponseEntity<?> save(
            @PathVariable String proprietarioId,
            @PathVariable Long imovelId,
            @PathVariable String ocupanteId,
            @RequestBody ContratoDTO dto
    ) {
        return ResponseEntity.ok(service.send(dto, proprietarioId, imovelId, ocupanteId));
    }

    @PatchMapping("users/{ocupanteId}/contratos/pendentes/{contratoId}/sign")
    public ResponseEntity<?> sign(
            @PathVariable String ocupanteId,
            @PathVariable Long contratoId
    ) {
        return ResponseEntity.ok(service.sign(contratoId, ocupanteId));
    }
}
