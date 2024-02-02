package io.github.jonasnascc.SeuSindico_API.entitiy.Fatura;

import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fatura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String preco;

    private Date dataVencimento;

    @ManyToOne
    private Contrato contrato;

}
