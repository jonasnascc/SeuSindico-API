package io.github.jonasnascc.SeuSindico_API.entitiy.Imovel;

import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Imovel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String descricao;

    private Integer quantidadeApartamentos;

    private Integer quantidadeAndares;

    private Integer apartamentosPorAndar;

    @ManyToOne
    private Proprietario proprietario;

    @ManyToOne
    private Ocupante ocupante;

    public Imovel(String nome, String descricao, Integer quantidadeApartamentos, Integer quantidadeAndares, Integer apartamentosPorAndar) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidadeApartamentos = quantidadeApartamentos;
        this.quantidadeAndares = quantidadeAndares;
        this.apartamentosPorAndar = apartamentosPorAndar;
    }
}
