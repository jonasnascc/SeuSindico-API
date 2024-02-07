package io.github.jonasnascc.SeuSindico_API.entitiy.Chamado;

import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chamado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoChamado tipo;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusChamado status;

    @ManyToOne
    private Ocupante ocupante;
}
