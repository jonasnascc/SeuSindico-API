package io.github.jonasnascc.SeuSindico_API.entitiy.Usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.jonasnascc.SeuSindico_API.entitiy.Chamado.Chamado;
import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("O")
public class Ocupante extends Usuario{
    @OneToMany(mappedBy = "ocupante")
    @JsonIgnore
    private Set<Contrato> contratos;

    @OneToMany(mappedBy = "ocupante")
    private Set<Chamado> chamados;

    public Ocupante(String nome, String cpf, String email, String senha, UserRole role) {
        super(nome, cpf, email, senha, role);
    }

    public void removeContrato (Contrato contrato) {
        if(contratos!=null) {
            contratos.removeIf(cont -> cont.getId().equals(contrato.getId()));
        }
    }
}
