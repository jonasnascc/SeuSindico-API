package io.github.jonasnascc.SeuSindico_API.dao;

import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.UserType;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findUsuarioByEmailOrCpf(String email, String cpf);

    @Query("Select p from Proprietario p where p.id = :id")
    Optional<Proprietario> findProprietarioById(String id);

    @Query("Select o from Ocupante o where o.id = :id")
    Optional<Ocupante> findOcupanteById(String id);

}
