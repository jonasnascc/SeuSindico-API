package io.github.jonasnascc.SeuSindico_API.dto.Imovel;

public record ImovelDTO(
        String nome,
        String decricao,
        Integer quantidadeApartamentos,
        Integer quantidadeAndares,
        Integer apartamentosPorAndar
) {}