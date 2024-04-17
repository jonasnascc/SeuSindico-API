package io.github.jonasnascc.SeuSindico_API.service.Imovel;

import io.github.jonasnascc.SeuSindico_API.dao.*;
import io.github.jonasnascc.SeuSindico_API.dto.Imovel.ImovelDTO;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Comodo;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Endereco;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Imovel;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Espaco;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import io.github.jonasnascc.SeuSindico_API.service.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ImovelService {
    private final ImovelRepository imovelRepository;

    private final UsuarioRepository usuarioRepository;

    private final EspacoRepository espacoRepository;

    private final ComodoRepository comodoRepository;

    private final EnderecoRepository enderecoRepository;

    public Long salvarImovel(ImovelDTO dto, String login) {
        Proprietario proprietario = proprietarioValidado(login);

        return persistirImovel(DtoConverter.convertImovelDto(dto), proprietario).getId();
    }

    public Set<ImovelDTO> listar(String login) {
        Proprietario proprietario = proprietarioValidado(login);

        return proprietario.getImoveis().stream()
                .map(DtoConverter::convertImovel).collect(Collectors.toSet());
    }

    public ImovelDTO procurarImovel(Long id, String login) {
        Proprietario proprietario = proprietarioValidado(login);

        Imovel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado."));

        if(imovel.getProprietario().getId().equals(proprietario.getId()))
            return DtoConverter.convertImovel(imovel);
        else throw new RuntimeException("Imóvel não encontrado.");
    }

    public ImovelDTO deletar(Long id, String login) {
        ImovelDTO dto = procurarImovel(id, login);

        Imovel imovel = imovelRepository.findById(dto.codigo()).get();
        imovelRepository.delete(imovel);

        Endereco endereco = imovel.getEndereco();
        enderecoRepository.delete(endereco);

        Set<Espaco> espacos = imovel.getEspacos();

        List<Comodo> comodos = new ArrayList<>();
        espacos.forEach(espaco -> {
            comodos.addAll(espaco.getComodos());
            espacoRepository.delete(espaco);
        });

        comodos.forEach(comodo -> comodoRepository.delete(comodo));

        return DtoConverter.convertImovel(imovel);
    }

    private Proprietario proprietarioValidado (String login) {
        return usuarioRepository.findProprietarioByLogin(login)
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado."));
    }

    private Imovel persistirImovel(Imovel imovel, Proprietario proprietario){
        Set<Espaco> espacos = imovel.getEspacos().stream().map(this::persistirEspaco).collect(Collectors.toSet());
        imovel.setEspacos(null);

        imovel.setProprietario(proprietario);

        Endereco endereco = enderecoRepository.save(imovel.getEndereco());
        imovel.setEndereco(endereco);

        Imovel saved = imovelRepository.save(imovel);

        endereco.setImovel(saved);
        enderecoRepository.save(endereco);

        espacos.forEach(res -> res.setImovel(saved));
        saved.setEspacos(espacos.stream().map(espacoRepository::save).collect(Collectors.toSet()));

        return imovelRepository.save(saved);
    }

    private Espaco persistirEspaco(Espaco espaco){
        Set<Comodo> comodos = espaco.getComodos().stream().map(comodoRepository::save).collect(Collectors.toSet());
        espaco.setComodos(null);

        Espaco saved = espacoRepository.save(espaco);
        comodos.forEach(comodo -> comodo.setEspaco(saved));

        saved.setComodos(comodos);

        return espacoRepository.save(saved);
    }


}
