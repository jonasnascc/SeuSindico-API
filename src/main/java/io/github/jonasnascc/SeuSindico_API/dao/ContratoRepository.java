package io.github.jonasnascc.SeuSindico_API.dao;

import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    @Query("select c from Contrato c where c.espaco.id = :espacoId")
    Optional<Contrato> findByEspacoId(Long espacoId);
}
