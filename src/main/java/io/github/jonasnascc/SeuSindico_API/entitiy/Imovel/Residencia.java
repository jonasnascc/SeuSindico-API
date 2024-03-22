package io.github.jonasnascc.SeuSindico_API.entitiy.Imovel;


import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
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
public class Residencia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer andar;

    private Integer numero;

    private Integer quantidadeComodos;

    private Double metrosQuadrados;

    @OneToMany(mappedBy = "residencia")
    private Set<Comodo> comodos;

    @OneToOne
    private Contrato contrato;

    @ManyToOne
    private Imovel imovel;

    public Residencia(Integer andar, Integer numero, Integer quantidadeComodos, Double metrosQuadrados, Set<Comodo> comodos) {
        this.andar = andar;
        this.numero = numero;
        this.quantidadeComodos = quantidadeComodos;
        this.metrosQuadrados = metrosQuadrados;
        this.comodos = comodos;
    }

    @PreRemove
    private void removerDependencias(){
        if(contrato!=null) contrato.setResidencia(null);
        if(imovel!=null) imovel.removerResidencia(this.id);
        if(comodos!=null) comodos.forEach(comodo -> comodo.setResidencia(null));
    }

    public void removeComodo(Long id) {
        if(comodos!=null) comodos.removeIf(comodo -> comodo.getId().equals(id));
    }
}
