package io.github.jonasnascc.SeuSindico_API.service.Usuario;

import io.github.jonasnascc.SeuSindico_API.dao.UsuarioRepository;
import io.github.jonasnascc.SeuSindico_API.dto.Auth.LoginDTO;
import io.github.jonasnascc.SeuSindico_API.dto.Auth.TokenOutputDTO;
import io.github.jonasnascc.SeuSindico_API.dto.Usuario.UsuarioDTO;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.UserRole;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Usuario;
import io.github.jonasnascc.SeuSindico_API.security.JwtTokenService;
import io.github.jonasnascc.SeuSindico_API.security.SecurityConfig;
import io.github.jonasnascc.SeuSindico_API.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final UsuarioRepository usuarioRepository;

    private final SecurityConfig securityConfig;

    public TokenOutputDTO login (LoginDTO dto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new TokenOutputDTO(jwtTokenService.generateToken(userDetails));
    }

    public TokenOutputDTO register (UsuarioDTO dto) {
        UserRole role = dto.role();

        Optional<Usuario> optUsuario = usuarioRepository.findUsuarioByEmailOrCpf(dto.email(), dto.cpf());
        if(optUsuario.isPresent()) throw new RuntimeException("Usuário informado já existe no banco de dados.");
        else System.out.println("not present");

        Usuario usuario;
        switch (role) {
            case PROPRIETARIO -> usuario = new Proprietario(dto.nome(), dto.cpf(), dto.email(), dto.senha(), role);
            case OCUPANTE ->  usuario = new Ocupante(dto.nome(), dto.cpf(), dto.email(), dto.senha(), role);
            default -> throw new RuntimeException("Tipo de usuário é inválido.");
        }
        usuario.setSenha(securityConfig.passwordEncoder().encode(dto.senha()));

        usuarioRepository.save(usuario);

        return login(new LoginDTO(dto.email(), dto.senha()));
    }

    public UsuarioDTO validateToken(String authorization) {
        String login = jwtTokenService.getSubject(authorization);

        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Usuario nao encontrado."));

        return new UsuarioDTO(
                usuario.getEmail(),
                "",
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getRole()
        );
    }
}
