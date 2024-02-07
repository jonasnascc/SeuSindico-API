package io.github.jonasnascc.SeuSindico_API.entitiy.Boleto;

import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Boleto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double preco;

    private Integer parcelaNumero;

    private LocalDate dataVencimento;

    private StatusBoleto status;

    @ManyToOne
    private Contrato contrato;

    public Boleto(Double preco, Integer parcelaNumero, LocalDate dataVencimento, StatusBoleto status) {
        this.preco = preco;
        this.parcelaNumero = parcelaNumero;
        this.dataVencimento = dataVencimento;
        this.status = status;
    }
}
