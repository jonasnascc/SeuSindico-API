package io.github.jonasnascc.SeuSindico_API.entitiy.Contrato;

import io.github.jonasnascc.SeuSindico_API.entitiy.Fatura.Fatura;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contrato {
    @Id
    private Long id;

    private BigInteger preco;

    private String observacoes;

    private Date dataInicio;

    private Date dataFim;

    private Integer intervaloDias;

    private Integer numeroParcelas;

    @ManyToOne
    private Proprietario proprietario;

    @ManyToOne
    private Ocupante ocupante;

    @OneToMany(mappedBy = "contrato")
    private Set<Fatura> faturas;

}
