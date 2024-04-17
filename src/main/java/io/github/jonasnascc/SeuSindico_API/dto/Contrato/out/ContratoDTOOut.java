package io.github.jonasnascc.SeuSindico_API.dto.Contrato.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.jonasnascc.SeuSindico_API.dto.Imovel.EspacoDTO;
import io.github.jonasnascc.SeuSindico_API.dto.Usuario.UsuarioSimplesDTO;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record ContratoDTOOut(
        Long id,
        Double preco,
        UsuarioSimplesDTO proprietario,
        UsuarioSimplesDTO ocupante,
        EspacoDTO espaco,
        String observacoes,
        @JsonFormat(pattern = "dd-MM-yyyy")
        @Nullable
        LocalDate dataInicio,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dataFim,
        Integer intervaloDias,
        Integer numeroParcelas,
        boolean pagamentoMensal
) {}
