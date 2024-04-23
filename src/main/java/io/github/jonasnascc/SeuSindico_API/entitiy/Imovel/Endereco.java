package io.github.jonasnascc.SeuSindico_API.entitiy.Imovel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String rua;

    private String numero;

    private String bairro;

    private String cidade;

    private String estado;

    private String cep;

    private String complemento;

    @OneToOne
    private Imovel imovel;

    public Endereco(
                    Long id,
                    String rua,
                    String numero,
                    String bairro,
                    String cidade,
                    String estado,
                    String cep,
                    String complemento) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.complemento = complemento;
    }

    @PreRemove
    private void removerDependencias() {
        if(imovel!=null) imovel.setEndereco(null);
    }
}
