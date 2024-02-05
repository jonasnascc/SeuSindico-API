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
        Imovel imovel = contrato.getImovel();
        Proprietario proprietario = contrato.getProprietario();
        Ocupante ocupante = contrato.getOcupante();

        return new ContratoDTOOut(
                contrato.getId(),
                contrato.getPreco(),
                new UsuarioSimplesDTO(proprietario.getId(), proprietario.getNome(), proprietario.getEmail()),
                new UsuarioSimplesDTO(ocupante.getId(), ocupante.getNome(), ocupante.getEmail()),
                null,
                contrato.getObservacoes(),
                contrato.getDataInicio(),
                contrato.getDataFim(),
                contrato.getIntervaloDias(),
                contrato.getNumeroParcelas(),
                contrato.isPagamentoMensal()
        );
    }

    public static Apartamento convertApartamentoDto(ApartamentoDTO dto) {
        ImovelDTO detalhes = dto.imovelDetalhes();
        Set<Habitacao> habitacoes = dto.habitacoes().stream()
                .map(DtoConverter::convertHabitacaoDto)
                .collect(Collectors.toSet());

        return new Apartamento(
                detalhes.nome(),
                detalhes.rua(),
                detalhes.numero(),
                detalhes.bairro(),
                detalhes.cidade(),
                detalhes.estado(),
                detalhes.cep(),
                detalhes.complemento(),
                dto.quantidadeAndares(),
                dto.habitacoesPorAndar(),
                habitacoes
        );
    }

    public static Casa convertCasaDto(CasaDTO dto) {
        ImovelDTO detalhes = dto.imovelDetalhes();
        Habitacao habitacao = convertHabitacaoDto(dto.habitacao());

        return new Casa(
                detalhes.nome(),
                detalhes.rua(),
                detalhes.numero(),
                detalhes.bairro(),
                detalhes.cidade(),
                detalhes.estado(),
                detalhes.cep(),
                detalhes.complemento(),
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
