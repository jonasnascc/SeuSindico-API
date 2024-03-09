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

    @PostMapping("/proprietario/imoveis/casas")
    public ResponseEntity<?> saveCasa (
            @RequestBody ImovelDTO dto
    )
    {
        return ResponseEntity.ok(service.saveCasa(dto, ""));
    }

    @PostMapping("/users/{userId}/imoveis/apartamentos")
    public ResponseEntity<?> saveApartamento (
            @PathVariable String userId,
            @RequestBody ImovelDTO dto
    )
    {
        return ResponseEntity.ok(service.saveApartamento(dto, userId));
    }

}
