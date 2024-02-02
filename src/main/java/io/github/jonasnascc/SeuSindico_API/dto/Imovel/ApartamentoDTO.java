package io.github.jonasnascc.SeuSindico_API.dto.Imovel;

import java.util.List;

public record ApartamentoDTO (
        Integer quantidadeAndares,
        Integer habitacoesPorAndar,

        ImovelDTO imovelDetalhes,
        List<HabitacaoDTO> habitacoes
){}
