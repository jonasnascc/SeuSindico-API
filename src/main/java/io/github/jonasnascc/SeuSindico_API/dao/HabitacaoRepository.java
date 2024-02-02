package io.github.jonasnascc.SeuSindico_API.dao;

import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Habitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitacaoRepository extends JpaRepository<Habitacao, Long> {

}
