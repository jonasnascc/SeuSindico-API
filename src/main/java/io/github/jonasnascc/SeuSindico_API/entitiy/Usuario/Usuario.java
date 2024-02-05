package io.github.jonasnascc.SeuSindico_API.entitiy.Usuario;

import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;

    private String cpf;

    private String email;

    private String senha;

    protected UserType tipo;

    public Usuario(String nome, String cpf, String email, String senha, UserType tipo) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public abstract Set<Contrato> getContratos();
}
