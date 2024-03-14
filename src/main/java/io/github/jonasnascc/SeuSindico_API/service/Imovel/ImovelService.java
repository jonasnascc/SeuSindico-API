package io.github.jonasnascc.SeuSindico_API.service.Imovel;

import io.github.jonasnascc.SeuSindico_API.dao.*;
import io.github.jonasnascc.SeuSindico_API.dto.Imovel.ImovelDTO;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Comodo;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Endereco;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Imovel;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Residencia;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Usuario;
import io.github.jonasnascc.SeuSindico_API.service.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ImovelService {
    private final ImovelRepository imovelRepository;

    private final UsuarioRepository usuarioRepository;

    private final ResidenciaRepository residenciaRepository;

    private final ComodoRepository comodoRepository;

    private final EnderecoRepository enderecoRepository;

    public Long salvarImovel(ImovelDTO dto, String login) {
        Proprietario proprietario = usuarioRepository.findProprietarioByLogin(login)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        return persistirImovel(DtoConverter.convertImovelDto(dto), proprietario).getId();
    }

    private Imovel persistirImovel(Imovel imovel, Proprietario proprietario){
        Set<Residencia> residencias = imovel.getResidencias().stream().map(this::persistirResidencia).collect(Collectors.toSet());
        imovel.setResidencias(null);

        imovel.setProprietario(proprietario);

        Endereco endereco = enderecoRepository.save(imovel.getEndereco());
        imovel.setEndereco(endereco);

        Imovel saved = imovelRepository.save(imovel);

        endereco.setImovel(saved);
        enderecoRepository.save(endereco);

        residencias.forEach(res -> res.setImovel(saved));
        saved.setResidencias(residencias.stream().map(residenciaRepository::save).collect(Collectors.toSet()));

        return imovelRepository.save(saved);
    }

    private Residencia persistirResidencia(Residencia residencia){
        Set<Comodo> comodos = residencia.getComodos().stream().map(comodoRepository::save).collect(Collectors.toSet());
        residencia.setComodos(null);

        Residencia saved = residenciaRepository.save(residencia);
        comodos.forEach(comodo -> comodo.setResidencia(saved));

        saved.setComodos(comodos);
        System.out.println("aqui chegou");
        return residenciaRepository.save(saved);
    }

}
