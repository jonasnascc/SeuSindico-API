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
