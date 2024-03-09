package io.github.jonasnascc.SeuSindico_API.dto.Usuario;

import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.UserRole;

public record UsuarioDTO (String email, String senha, String nome, String cpf, UserRole role) {}
