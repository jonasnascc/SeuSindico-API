package io.github.jonasnascc.SeuSindico_API.dto.Imovel;


import java.util.List;

public record HabitacaoDTO(
        Long codigoImovel,
        Integer andar,
        Integer numero,
        Integer quantidadeComodos,
        Double metrosQuadrados,
        List<ComodoDTO> comodos
) {}
