package io.github.jonasnascc.SeuSindico_API.dto.Imovel;


import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.EspacoType;

import java.util.List;

public record EspacoDTO(
        Long espacoId,
        Integer andar,
        Integer numero,
        Double metrosQuadrados,
        List<ComodoDTO> comodos,
        EspacoType tipo
) {}
