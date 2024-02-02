package io.github.jonasnascc.SeuSindico_API.controller.Usuario;

import io.github.jonasnascc.SeuSindico_API.dto.Usuario.UsuarioDTO;
import io.github.jonasnascc.SeuSindico_API.service.Usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping("signin")
    public ResponseEntity<?> register(
            @RequestBody UsuarioDTO dto
    ) {
        return ResponseEntity.ok(service.register(dto));
    }

    @PostMapping("auth")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<?> getUser(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(null);
    }

    @PutMapping("users/{id}")
    public ResponseEntity<?> update(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> delete(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(null);
    }



}
