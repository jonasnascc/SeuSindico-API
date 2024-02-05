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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Imovel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String rua;

    private String numero;

    private String bairro;

    private String cidade;

    private String estado;

    private String cep;

    private String complemento;

    private ImovelType tipo;

    @ManyToOne
    private Proprietario proprietario;

    @OneToOne(mappedBy = "imovel")
    private Contrato contrato;

    public Imovel(
            String nome,
            String rua,
            String numero,
            String bairro,
            String cidade,
            String estado,
            String cep,
            String complemento,
            ImovelType tipo
    ){
        this.nome = nome;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.complemento = complemento;
        this.tipo = tipo;
    }
}
