package io.github.jonasnascc.SeuSindico_API.dao;

import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
