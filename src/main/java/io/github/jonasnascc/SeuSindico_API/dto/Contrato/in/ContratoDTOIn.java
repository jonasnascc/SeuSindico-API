package io.github.jonasnascc.SeuSindico_API.dto.Contrato.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record ContratoDTOIn(
        Double preco,
        Double valorAdiantado,
        String observacoes,
        @JsonFormat(pattern = "dd-MM-yyyy")
        @Nullable
        LocalDate dataInicio,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dataFim,
        Integer intervaloDias,
        Integer numeroParcelas,

        boolean pagamentoMensal,
        boolean prePago
) {}
