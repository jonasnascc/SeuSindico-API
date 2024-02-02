package io.github.jonasnascc.SeuSindico_API.entitiy.Usuario;

import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Imovel;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Ocupante extends Usuario{

    public static final UserType tipo = UserType.OCUPANTE;


    @OneToMany(mappedBy = "ocupante")
    private Set<Contrato> contratos;

    public Ocupante(String email, String senha, String nome, String cpf) {
        super(nome, cpf, email, senha, Ocupante.tipo);
    }
}
