package io.github.jonasnascc.SeuSindico_API.service.Imovel;

import io.github.jonasnascc.SeuSindico_API.dao.ComodoRepository;
import io.github.jonasnascc.SeuSindico_API.dao.HabitacaoRepository;
import io.github.jonasnascc.SeuSindico_API.dao.ImovelRepository;
import io.github.jonasnascc.SeuSindico_API.dao.UsuarioRepository;
import io.github.jonasnascc.SeuSindico_API.dto.Imovel.*;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Apartamento;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Casa;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Comodo;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Habitacao;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import io.github.jonasnascc.SeuSindico_API.service.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImovelService {
    private final ImovelRepository imovelRepository;

    private final UsuarioRepository usuarioRepository;

    private final HabitacaoRepository habitacaoRepository;

    private final ComodoRepository comodoRepository;

    public Long saveCasa(CasaDTO dto, String userId) {
        Proprietario proprietario = usuarioRepository.findProprietarioById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário inválido ou não encontrado."));

        Casa casa = DtoConverter.convertCasaDto(dto);

        Habitacao habitacao = persistHabitacao(casa.getHabitacao());
        casa.setHabitacao(habitacao);

        casa.setProprietario(proprietario);

        Casa saved = imovelRepository.save(casa);

        saved.getHabitacao().setImovel(saved);
        habitacaoRepository.save(habitacao);

        return saved.getId();
    }

    public Long saveApartamento(ApartamentoDTO dto, String userId) {
        Proprietario proprietario = usuarioRepository.findProprietarioById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário inválido ou não encontrado."));

        Apartamento apartamento = DtoConverter.convertApartamentoDto(dto);
        apartamento.setProprietario(proprietario);

        Set<Habitacao> habitacoes = apartamento.getHabitacoes().stream()
                .map(this::persistHabitacao)
                .collect(Collectors.toSet());

        apartamento.setHabitacoes(habitacoes);

        Apartamento saved = imovelRepository.save(apartamento);

        habitacoes.forEach(hab -> {
            hab.setImovel(saved);
            habitacaoRepository.save(hab);
        });

        return saved.getId();
    }

    public Habitacao persistHabitacao(Habitacao habitacao) {
        Set<Comodo> comodos = new HashSet<>(habitacao.getComodos());
        habitacao.getComodos().clear();

        Habitacao saved = habitacaoRepository.save(habitacao);

        saved.setComodos(
                comodos.stream().map(comodo -> {
                    comodo.setHabitacao(saved);
                    return comodoRepository.save(comodo);
                }).collect(Collectors.toSet())
        );

        return habitacaoRepository.save(saved);
    }





}
