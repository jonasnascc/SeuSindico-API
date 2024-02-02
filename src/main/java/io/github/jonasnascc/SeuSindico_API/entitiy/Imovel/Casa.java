package io.github.jonasnascc.SeuSindico_API.entitiy.Imovel;

import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Casa extends Imovel{
    @OneToOne(mappedBy = "imovel")
    private Habitacao habitacao;

    public Casa(String nome,
                String rua,
                String numero,
                String bairro,
                String cidade,
                String estado,
                String cep,
                String complemento,
                Habitacao habitacao)
    {
        super(nome, rua, numero, bairro, cidade, estado, cep, complemento, ImovelType.CASA);
        this.habitacao = habitacao;
    }
}
