package io.github.jonasnascc.SeuSindico_API.entitiy.Contrato;

import io.github.jonasnascc.SeuSindico_API.entitiy.Fatura.Fatura;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Imovel;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double preco;

    private String observacoes;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private Integer intervaloDias;

    private Integer numeroParcelas;

    private ContratoStatus status;

    @OneToOne
    private Imovel imovel;

    @ManyToOne
    private Proprietario proprietario;

    @ManyToOne
    private Ocupante ocupante;

    @OneToMany(mappedBy = "contrato")
    private Set<Fatura> faturas;

    public Contrato(Double preco, String observacoes, LocalDate dataInicio, LocalDate dataFim, Integer intervaloDias, Integer numeroParcelas, Imovel imovel, Proprietario proprietario, Ocupante ocupante, ContratoStatus status) {
        this.preco = preco;
        this.observacoes = observacoes;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.intervaloDias = intervaloDias;
        this.numeroParcelas = numeroParcelas;
        this.imovel = imovel;
        this.proprietario = proprietario;
        this.ocupante = ocupante;
        this.status = status;
    }

    public Contrato(Double preco, String observacoes, LocalDate dataInicio, LocalDate dataFim, Integer intervaloDias, Integer numeroParcelas, ContratoStatus status) {
        this.preco = preco;
        this.observacoes = observacoes;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.intervaloDias = intervaloDias;
        this.numeroParcelas = numeroParcelas;
        this.status = status;
    }

    public void setAssinado() {
        this.status = ContratoStatus.ASSINADO;
    }

    public void setPendente() {
        this.status = ContratoStatus.PENDENTE;
    }
}
