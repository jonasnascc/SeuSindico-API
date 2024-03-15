package io.github.jonasnascc.SeuSindico_API.dto.Imovel;


import java.util.List;

public record ResidenciaDTO(
        Long residenciaId,
        Integer andar,
        Integer numero,
        Integer quantidadeComodos,
        Double metrosQuadrados,
        List<ComodoDTO> comodos
) {}
