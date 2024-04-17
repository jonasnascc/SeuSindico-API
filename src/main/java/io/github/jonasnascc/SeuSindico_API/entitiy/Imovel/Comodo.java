package io.github.jonasnascc.SeuSindico_API.entitiy.Imovel;

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
public class Comodo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private Integer metrosQuadrados;

    private String detalhes;

    @ManyToOne
    private Espaco espaco;

    public Comodo(String nome, Integer metrosQuadrados, String detalhes) {
        this.nome = nome;
        this.metrosQuadrados = metrosQuadrados;
        this.detalhes = detalhes;
    }

    @PreRemove
    private void removeDependencies(){
        if(espaco !=null) espaco.removeComodo(this.id);
    }
}
