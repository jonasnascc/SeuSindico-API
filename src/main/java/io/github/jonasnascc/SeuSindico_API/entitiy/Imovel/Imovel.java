package io.github.jonasnascc.SeuSindico_API.entitiy.Imovel;

import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
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

    private Integer espacosPorAndar;

    @OneToOne
    private Endereco endereco;

    @ManyToOne
    private Proprietario proprietario;

    @OneToMany(mappedBy = "imovel")
    private Set<Espaco> espacos;

    public Imovel(
            String nome,
            String descricao,
            Integer quantidadeAndares,
            Integer espacosPorAndar,
            Endereco endereco
    ){
        this.nome = nome;
        this.descricao = descricao;
        this.quantidadeAndares = quantidadeAndares;
        this.espacosPorAndar = espacosPorAndar;
        this.endereco = endereco;
    }

    @PreRemove
    private void removerDependencias() {
        proprietario.removeImovel(this.id);
        if(endereco!=null) endereco.setImovel(null);
        if(espacos !=null) espacos.forEach(espaco -> espaco.setImovel(null));

    }

    public void removerEspaco(Long id) {
        if(espacos !=null) espacos.removeIf(espaco -> espaco.getId().equals(id));
    }
}
