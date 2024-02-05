package io.github.jonasnascc.SeuSindico_API.entitiy.Usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Imovel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Proprietario extends Usuario{
    public static final UserType tipo = UserType.PROPRIETARIO;

    @OneToMany(mappedBy = "proprietario")
    @JsonIgnore
    private Set<Imovel> imoveis;

    @OneToMany(mappedBy = "proprietario")
    @JsonIgnore
    private Set<Contrato> contratos;

    public Proprietario(String email, String senha, String nome, String cpf) {
        super(nome, cpf, email, senha, Proprietario.tipo);
    }

    public void addImovel (Imovel imovel) {
        if(imoveis == null) imoveis = new HashSet<>();
        if(imoveis.stream().noneMatch(im -> imovel.getId().equals(im.getId()))) {
            imoveis.add(imovel);
        }
    }

    public void removeImovel (Long id) {
        if(imoveis!=null) imoveis.removeIf(im -> im.getId().equals(id));
    }

    public void removeContrato (Contrato contrato) {
        if(contratos!=null) {
            contratos.removeIf(cont -> cont.getId().equals(contrato.getId()));
        }
    }
}
