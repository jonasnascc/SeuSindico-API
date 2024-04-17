package io.github.jonasnascc.SeuSindico_API.entitiy.Imovel;


import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Espaco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer andar;

    private Integer numero;

    private Double metrosQuadrados;

    @Enumerated(EnumType.STRING)
    private EspacoType tipo;

    @OneToMany(mappedBy = "espaco")
    private Set<Comodo> comodos;

    @OneToOne
    private Contrato contrato;

    @ManyToOne
    private Imovel imovel;

    public Espaco(Integer andar, Integer numero, Double metrosQuadrados, Set<Comodo> comodos, EspacoType tipo) {
        this.andar = andar;
        this.numero = numero;
        this.metrosQuadrados = metrosQuadrados;
        this.comodos = comodos;
        this.tipo = tipo;
    }

    @PreRemove
    private void removerDependencias(){
        if(contrato!=null) contrato.setEspaco(null);
        if(imovel!=null) imovel.removerEspaco(this.id);
        if(comodos!=null) comodos.forEach(comodo -> comodo.setEspaco(null));
    }

    public void removeComodo(Long id) {
        if(comodos!=null) comodos.removeIf(comodo -> comodo.getId().equals(id));
    }
}
