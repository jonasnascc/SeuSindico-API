package io.github.jonasnascc.SeuSindico_API.entitiy.Imovel;

import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Imovel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String descricao;

    private Integer quantidadeAndares;

    private Integer residenciasPorAndar;

    @OneToOne
    private Endereco endereco;

    @ManyToOne
    private Proprietario proprietario;

    @OneToMany(mappedBy = "imovel")
    private Set<Residencia> residencias;

    @Enumerated(EnumType.STRING)
    private ImovelType tipo;

    public Imovel(
            String nome,
            String descricao,
            Integer quantidadeAndares,
            Integer residenciasPorAndar,
            Endereco endereco,
            ImovelType tipo
    ){
        this.nome = nome;
        this.descricao = descricao;
        this.quantidadeAndares = quantidadeAndares;
        this.residenciasPorAndar = residenciasPorAndar;
        this.endereco = endereco;
        this.tipo = tipo;
    }
}
