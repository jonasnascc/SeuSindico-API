package io.github.jonasnascc.SeuSindico_API.dto.Imovel;

import java.util.List;

public record ImovelDTO(
        Long codigo,
        String nome,
        String descricao,
        Integer quantidadeAndares,
        Integer espacosPorAndar,
        EnderecoDTO endereco,
        List<EspacoDTO> espacos
) {}