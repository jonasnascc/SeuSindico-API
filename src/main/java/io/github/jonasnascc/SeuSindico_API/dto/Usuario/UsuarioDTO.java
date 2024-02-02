package io.github.jonasnascc.SeuSindico_API.dto.Usuario;

import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.UserType;

public record UsuarioDTO (String email, String senha, String nome, String cpf, UserType tipo) {}
