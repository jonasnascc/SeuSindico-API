package io.github.jonasnascc.SeuSindico_API.entitiy.Contrato;

import io.github.jonasnascc.SeuSindico_API.entitiy.Boleto.Boleto;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Imovel;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
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

    @Enumerated(EnumType.STRING)
    private ContratoStatus status;

    private boolean pagamentoMensal;

    @OneToOne
    private Imovel imovel;

    @ManyToOne
    private Proprietario proprietario;

    @ManyToOne
    private Ocupante ocupante;

    @OneToMany(mappedBy = "contrato")
    private Set<Boleto> boletos;

    public Contrato(Double preco,
                    String observacoes,
                    LocalDate dataInicio,
                    LocalDate dataFim,
                    Integer intervaloDias,
                    Integer numeroParcelas,
                    Imovel imovel,
                    Proprietario proprietario,
                    Ocupante ocupante,
                    ContratoStatus status,
                    boolean pagamentoMensal)
    {
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
        this.pagamentoMensal = pagamentoMensal;
    }

    public Contrato(Double preco,
                    String observacoes,
                    LocalDate dataInicio,
                    LocalDate dataFim,
                    Integer intervaloDias,
                    Integer numeroParcelas,
                    ContratoStatus status,
                    boolean pagamentoMensal)
    {
        this.preco = preco;
        this.observacoes = observacoes;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.intervaloDias = intervaloDias;
        this.numeroParcelas = numeroParcelas;
        this.status = status;
        this.pagamentoMensal = pagamentoMensal;
    }

    @PreRemove
    public void removeFromDependencies() {
        imovel.setContrato(null);
        proprietario.removeContrato(this);
        if(ocupante!=null) ocupante.removeContrato(this);
    }

    public void addBoleto(Boleto boleto) {
        if(boletos == null) boletos = new HashSet<>();
        boletos.add(boleto);
    }

    public void removeBoleto(Long boletoId) {
        boletos.removeIf(bol -> bol.getId().equals(boletoId));
    }

    public void setAssinado() {
        this.status = ContratoStatus.ASSINADO;
    }

    public void setPendente() {
        this.status = ContratoStatus.PENDENTE;
    }
}
