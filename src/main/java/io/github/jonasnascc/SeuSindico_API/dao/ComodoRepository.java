package io.github.jonasnascc.SeuSindico_API.dao;

import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Comodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComodoRepository extends JpaRepository<Comodo, Long> {
}
