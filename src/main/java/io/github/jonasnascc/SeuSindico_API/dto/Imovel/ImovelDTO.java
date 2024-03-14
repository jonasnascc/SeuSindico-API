package io.github.jonasnascc.SeuSindico_API.dto.Imovel;

import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.ImovelType;

import java.util.List;

public record ImovelDTO(
    String nome,
    String descricao,
    Integer quantidadeAndares,
    Integer residenciasPorAndar,
    EnderecoDTO endereco,
    List<ResidenciaDTO> residencias,
    ImovelType tipo
) {}