package io.github.jonasnascc.SeuSindico_API.entitiy.Imovel;


import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Imovel;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Apartamento extends Imovel {
    private Integer quantidadeAndares;

    private Integer habitacoesPorAndar;

    @OneToMany(mappedBy = "imovel")
    private Set<Habitacao> habitacoes;

    public Apartamento(String nome,
                       String rua,
                       String numero,
                       String bairro,
                       String cidade,
                       String estado,
                       String cep,
                       String complemento,
                       Integer quantidadeAndares,
                       Integer habitacoesPorAndar,
                       Set<Habitacao> habitacoes)
    {
        super(nome, rua, numero, bairro, cidade, estado, cep, complemento, ImovelType.APARTAMENTO);
        this.quantidadeAndares = quantidadeAndares;
        this.habitacoesPorAndar = habitacoesPorAndar;
        this.habitacoes = habitacoes;
    }
}
