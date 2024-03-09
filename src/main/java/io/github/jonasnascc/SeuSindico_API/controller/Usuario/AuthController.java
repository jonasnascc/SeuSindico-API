package io.github.jonasnascc.SeuSindico_API.controller.Usuario;

import io.github.jonasnascc.SeuSindico_API.dto.Auth.LoginDTO;
import io.github.jonasnascc.SeuSindico_API.dto.Usuario.UsuarioDTO;
import io.github.jonasnascc.SeuSindico_API.security.JwtTokenService;
import io.github.jonasnascc.SeuSindico_API.service.Usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioService service;

    @PostMapping("login")
    public ResponseEntity<?> login(
            @RequestBody LoginDTO dto
    ) {
        return ResponseEntity.ok(service.login(dto));
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup (
            @RequestBody UsuarioDTO dto
    ) {
        return new ResponseEntity<>(service.register(dto), HttpStatus.CREATED);
    }

    @GetMapping("token/validate")
    public ResponseEntity<?> validateToken (
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok(service.validateToken(authorization));
    }
}
