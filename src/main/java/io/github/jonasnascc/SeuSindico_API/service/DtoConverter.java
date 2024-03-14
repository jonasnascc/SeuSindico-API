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

    public static ImovelDTO convertImovel(Imovel imovel){
        return new ImovelDTO(
                imovel.getId(),
                imovel.getNome(),
                imovel.getDescricao(),
                imovel.getQuantidadeAndares(),
                imovel.getResidenciasPorAndar(),
                convertEndereco(imovel.getEndereco()),
                imovel.getResidencias().stream().map(DtoConverter::convertResidencia).collect(Collectors.toList()),
                imovel.getTipo()
        );
    }

    public static Imovel convertImovelDto(ImovelDTO dto){
        Imovel imovel = new Imovel(
                dto.nome(),
                dto.descricao(),
                dto.quantidadeAndares(),
                dto.residenciasPorAndar(),
                convertEnderecoDto(dto.endereco()),
                dto.tipo()
        );

        imovel.setResidencias(
                dto.residencias().stream()
                        .map(DtoConverter::convertResidenciaDto).collect(Collectors.toSet())
        );

        return imovel;
    }

    public static EnderecoDTO convertEndereco(Endereco endereco){
        return new EnderecoDTO(
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getComplemento()
        );
    }

    public static Endereco convertEnderecoDto(EnderecoDTO dto) {
        return new Endereco(
                dto.rua(),
                dto.numero(),
                dto.bairro(),
                dto.cidade(),
                dto.estado(),
                dto.cep(),
                dto.complemento()
        );
    }

    public static ResidenciaDTO convertResidencia(Residencia residencia){
        return new ResidenciaDTO(
                residencia.getAndar(),
                residencia.getNumero(),
                residencia.getQuantidadeComodos(),
                residencia.getMetrosQuadrados(),
                residencia.getComodos().stream().map(DtoConverter::convertComodo).collect(Collectors.toList())
        );
    }

    public static Residencia convertResidenciaDto(ResidenciaDTO dto) {
        return new Residencia(
                dto.andar(),
                dto.numero(),
                dto.quantidadeComodos(),
                dto.metrosQuadrados(),
                dto.comodos().stream().map(DtoConverter::convertComodoDto).collect(Collectors.toSet())
        );
    }

    public static ComodoDTO convertComodo(Comodo comodo){
        return new ComodoDTO(
                comodo.getNome(),
                comodo.getMetrosQuadrados(),
                comodo.getDetalhes()
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
