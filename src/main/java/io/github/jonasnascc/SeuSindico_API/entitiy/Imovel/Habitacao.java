package io.github.jonasnascc.SeuSindico_API.entitiy.Imovel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Habitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer andar;

    private Integer numero;

    private Integer quantidadeComodos;

    private Double metrosQuadrados;

    @OneToMany(mappedBy = "habitacao")
    private Set<Comodo> comodos;

    @ManyToOne
    private Imovel imovel;

    public Habitacao(Integer andar,
                     Integer numero,
                     Integer quantidadeComodos,
                     Double metrosQuadrados,
                     Set<Comodo> comodos)
    {
        this.andar = andar;
        this.numero = numero;
        this.quantidadeComodos = quantidadeComodos;
        this.metrosQuadrados = metrosQuadrados;
        this.comodos = comodos;
    }
}
