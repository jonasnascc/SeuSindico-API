package io.github.jonasnascc.SeuSindico_API.controller.Contrato;

import io.github.jonasnascc.SeuSindico_API.dto.Contrato.in.ContratoDTOIn;
import io.github.jonasnascc.SeuSindico_API.security.JwtTokenService;
import io.github.jonasnascc.SeuSindico_API.service.Contrato.ContratoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContratoController {
    private final ContratoService service;
    private final JwtTokenService jwtTokenService;

    @PostMapping("proprietario/imoveis/{imovelId}/residencias/{residenciaId}/contratos/ocupante/{cpfOcupante}")
    public ResponseEntity<?> save(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long imovelId,
            @PathVariable Long residenciaId,
            @PathVariable String cpfOcupante,
            @RequestBody ContratoDTOIn dto
    ) {
        String login = jwtTokenService.getSubject(authorization);
        return ResponseEntity.ok(service.enviar(dto, imovelId, residenciaId, cpfOcupante, login));
    }

    @PatchMapping("ocupante/contratos/pendentes/{contratoId}/assinar")
    public ResponseEntity<?> assinar(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long contratoId
    ) {
        String login = jwtTokenService.getSubject(authorization);
        return ResponseEntity.ok(service.assinar(contratoId, login));
    }

    @GetMapping("usuario/contratos")
    public ResponseEntity<?> listar(
            @RequestHeader("Authorization") String authorization
    ){
        String login = jwtTokenService.getSubject(authorization);
        return ResponseEntity.ok(service.listar(login));
    }

    @DeleteMapping("usuario/contratos/{id}/cancelar")
    public ResponseEntity<?> cancelar(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id
    ) {
        String login = jwtTokenService.getSubject(authorization);
        service.cancelar(id, login);
        return ResponseEntity.ok().build();
    }
}
