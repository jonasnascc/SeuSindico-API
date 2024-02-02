package io.github.jonasnascc.SeuSindico_API.service.Usuario;

import io.github.jonasnascc.SeuSindico_API.dao.UsuarioRepository;
import io.github.jonasnascc.SeuSindico_API.dto.Usuario.UsuarioDTO;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.UserType;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public String register (UsuarioDTO dto) {
        UserType tipo = dto.tipo();

        Optional<Usuario> optUsuario = usuarioRepository.findUsuarioByEmailOrCpf(dto.email(), dto.cpf());
        if(optUsuario.isPresent()) throw new RuntimeException("Usuário informado já existe no banco de dados.");

        Usuario usuario;
        switch (tipo) {
            case PROPRIETARIO -> usuario = new Proprietario(dto.email(), dto.senha(), dto.nome(), dto.cpf());
            case OCUPANTE ->  usuario = new Ocupante(dto.email(), dto.senha(), dto.nome(), dto.cpf());
            default -> throw new RuntimeException("Tipo de usuário é inválido.");
        }

        return usuarioRepository.save(usuario).getId();
    }
}
