package io.github.jonasnascc.SeuSindico_API.controller.Imovel;

import io.github.jonasnascc.SeuSindico_API.dto.Imovel.ImovelDTO;
import io.github.jonasnascc.SeuSindico_API.security.JwtTokenService;
import io.github.jonasnascc.SeuSindico_API.service.Imovel.ImovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImovelController {
    private final ImovelService service;

    private final JwtTokenService jwtTokenService;

    @PostMapping("/proprietario/imoveis")
    public ResponseEntity<?> saveCasa (
            @RequestHeader("Authorization") String authorization,
            @RequestBody ImovelDTO dto
    )
    {
        String login = jwtTokenService.getSubject(authorization);
        return ResponseEntity.ok(service.salvarImovel(dto, login));
    }

}
