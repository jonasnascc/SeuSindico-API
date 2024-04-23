package io.github.jonasnascc.SeuSindico_API.dto.Imovel;

public record EnderecoDTO(
        Long enderecoId,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep,
        String complemento
) {}
