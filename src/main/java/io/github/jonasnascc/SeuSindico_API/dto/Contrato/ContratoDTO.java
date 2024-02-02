package io.github.jonasnascc.SeuSindico_API.dto.Contrato;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record ContratoDTO(
        Double preco,
        String observacoes,
        @JsonFormat(pattern = "dd-MM-yyyy")
        @Nullable
        LocalDate dataInicio,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dataFim,
        Integer intervaloDias,
        Integer numeroParcelas
) {}
