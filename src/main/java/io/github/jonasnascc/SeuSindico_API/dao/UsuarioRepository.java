package io.github.jonasnascc.SeuSindico_API.dao;

import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
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

    @Query("select u from Usuario u where (u.email = :login or u.cpf = :login)")
    Optional<Usuario> findByLogin(String login);

    @Query("select p from Proprietario p where (p.cpf = :login or p.email = :login)")
    Optional<Proprietario> findProprietarioByLogin(String login);

    @Query("select o from Ocupante o where (o.cpf = :login or o.email = :login)")
    Optional<Ocupante> findOcupanteByLogin(String login);

}
