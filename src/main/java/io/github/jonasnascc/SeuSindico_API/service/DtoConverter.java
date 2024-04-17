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

import java.util.stream.Collectors;

public abstract class DtoConverter {

    public static Contrato convertContratoDto(ContratoDTOIn dto) {
        return new Contrato(
                dto.preco(),
                dto.valorAdiantado(),
                dto.observacoes(),
                dto.dataInicio(),
                dto.dataFim(),
                dto.pagamentoMensal() ? 30 : dto.intervaloDias(),
                dto.numeroParcelas(),
                ContratoStatus.PENDENTE,
                dto.pagamentoMensal(),
                dto.prePago()
        );
    }

    public static ContratoDTOOut convertContrato(Contrato contrato) {
        Proprietario proprietario = contrato.getProprietario();
        Ocupante ocupante = contrato.getOcupante();
        return new ContratoDTOOut(
                contrato.getId(),
                contrato.getPreco(),
                new UsuarioSimplesDTO(proprietario.getNome(), proprietario.getCpf()),
                new UsuarioSimplesDTO(ocupante.getNome(), ocupante.getCpf()),
                convertResidencia(contrato.getEspaco()),
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
                imovel.getEspacosPorAndar(),
                convertEndereco(imovel.getEndereco()),
                imovel.getEspacos().stream().map(DtoConverter::convertResidencia).collect(Collectors.toList())
        );
    }

    public static Imovel convertImovelDto(ImovelDTO dto){
        Imovel imovel = new Imovel(
                dto.nome(),
                dto.descricao(),
                dto.quantidadeAndares(),
                dto.espacosPorAndar(),
                convertEnderecoDto(dto.endereco())
        );

        imovel.setEspacos(
                dto.espacos().stream()
                        .map(DtoConverter::convertEspacoDto).collect(Collectors.toSet())
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

    public static EspacoDTO convertResidencia(Espaco espaco){
        return new EspacoDTO(
                espaco.getId(),
                espaco.getAndar(),
                espaco.getNumero(),
                espaco.getMetrosQuadrados(),
                espaco.getComodos().stream().map(DtoConverter::convertComodo).collect(Collectors.toList()),
                espaco.getTipo()
        );
    }

    public static Espaco convertEspacoDto(EspacoDTO dto) {
        return new Espaco(
                dto.andar(),
                dto.numero(),
                dto.metrosQuadrados(),
                dto.comodos().stream().map(DtoConverter::convertComodoDto).collect(Collectors.toSet()),
                dto.tipo()
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
