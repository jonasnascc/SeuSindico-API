package io.github.jonasnascc.SeuSindico_API.service;

import io.github.jonasnascc.SeuSindico_API.dto.Contrato.in.ContratoDTOIn;
import io.github.jonasnascc.SeuSindico_API.dto.Contrato.out.ContratoDTOOut;
import io.github.jonasnascc.SeuSindico_API.dto.Imovel.*;
import io.github.jonasnascc.SeuSindico_API.dto.Usuario.UsuarioSimplesDTO;
import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.ContratoStatus;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.*;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class DtoConverter {

    public static Contrato convertContratoDto(ContratoDTOIn dto) {
        return new Contrato(
                dto.preco(),
                dto.observacoes(),
                dto.dataInicio(),
                dto.dataFim(),
                dto.pagamentoMensal() ? 30 : dto.intervaloDias(),
                dto.numeroParcelas(),
                ContratoStatus.PENDENTE,
                dto.pagamentoMensal()
        );
    }

    public static ContratoDTOOut convertContrato(Contrato contrato) {
        ImovelType tipo = contrato.getImovel().getTipo();
        ImovelDTO imovelDTO = tipo.equals(ImovelType.CASA) ?
                convertCasa((Casa)contrato.getImovel()) : convertApartamento((Apartamento)contrato.getImovel());

        Proprietario proprietario = contrato.getProprietario();
        Ocupante ocupante = contrato.getOcupante();
        return new ContratoDTOOut(
                contrato.getId(),
                contrato.getPreco(),
                new UsuarioSimplesDTO(proprietario.getId(), proprietario.getNome(), proprietario.getEmail()),
                new UsuarioSimplesDTO(ocupante.getId(), ocupante.getNome(), ocupante.getEmail()),
                imovelDTO,
                contrato.getObservacoes(),
                contrato.getDataInicio(),
                contrato.getDataFim(),
                contrato.getIntervaloDias(),
                contrato.getNumeroParcelas(),
                contrato.isPagamentoMensal()
        );
    }

    public static ImovelDTO convertCasa(Casa casa) {
        List<HabitacaoDTO> habitacoes = new ArrayList<>();
        habitacoes.add(convertHabitacao(casa.getHabitacao(), casa.getId()));
        return new ImovelDTO(
                casa.getNome(),
                casa.getRua(),
                casa.getNumero(),
                casa.getBairro(),
                casa.getCidade(),
                casa.getEstado(),
                casa.getCep(),
                casa.getComplemento(),
                casa.getTipo(),
                habitacoes,
                1,
                1
        );
    }
    public static ImovelDTO convertApartamento(Apartamento apartamento) {
        return new ImovelDTO(
                apartamento.getNome(),
                apartamento.getRua(),
                apartamento.getNumero(),
                apartamento.getBairro(),
                apartamento.getCidade(),
                apartamento.getEstado(),
                apartamento.getCep(),
                apartamento.getComplemento(),
                apartamento.getTipo(),
                apartamento.getHabitacoes().stream().map(hab -> convertHabitacao(hab, apartamento.getId())).collect(Collectors.toList()),
                apartamento.getQuantidadeAndares(),
                apartamento.getHabitacoesPorAndar()
        );
    }

    public static HabitacaoDTO convertHabitacao(Habitacao habitacao, Long codigoImovel) {
        return new HabitacaoDTO(
                codigoImovel,
                habitacao.getAndar(),
                habitacao.getNumero(),
                habitacao.getQuantidadeComodos(),
                habitacao.getMetrosQuadrados(),
                habitacao.getComodos().stream().map(comodo -> convertComodo(comodo, habitacao.getId())).toList()
        );
    }

    public static ComodoDTO convertComodo(Comodo comodo, Long codigoHabitacao) {
        return new ComodoDTO(comodo.getNome(), comodo.getMetrosQuadrados(), comodo.getDetalhes(), codigoHabitacao);
    }

    public static Apartamento convertApartamentoDto(ImovelDTO dto) {
        Set<Habitacao> habitacoes = dto.habitacoes().stream()
                .map(DtoConverter::convertHabitacaoDto)
                .collect(Collectors.toSet());

        return new Apartamento(
                dto.nome(),
                dto.rua(),
                dto.numero(),
                dto.bairro(),
                dto.cidade(),
                dto.estado(),
                dto.cep(),
                dto.complemento(),
                dto.quantidadeAndares(),
                dto.habitacoesPorAndar(),
                habitacoes
        );
    }

    public static Casa convertCasaDto(ImovelDTO dto) {
        Habitacao habitacao = convertHabitacaoDto(dto.habitacoes().get(0));

        return new Casa(
                dto.nome(),
                dto.rua(),
                dto.numero(),
                dto.bairro(),
                dto.cidade(),
                dto.estado(),
                dto.cep(),
                dto.complemento(),
                habitacao
        );
    }

    public static Habitacao convertHabitacaoDto(HabitacaoDTO dto) {
        Set<Comodo> comodos = dto.comodos().stream()
                .map(DtoConverter::convertComodoDto)
                .collect(Collectors.toSet());

        return new Habitacao(
                dto.andar(),
                dto.numero(),
                dto.quantidadeComodos(),
                dto.metrosQuadrados(),
                comodos
        );
    }

    public static Comodo convertComodoDto(ComodoDTO dto) {
        return new Comodo(
                dto.nome(),
                dto.metrosQuadrados(),
                dto.detalhes()
        );
    }
}
