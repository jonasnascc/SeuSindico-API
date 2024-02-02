package io.github.jonasnascc.SeuSindico_API.dto.Imovel;

import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.ImovelType;

public record ImovelDTO(
         String nome,

         String rua,

         String numero,

         String bairro,

         String cidade,

         String estado,

         String cep,

         String complemento,

         ImovelType tipo
) {}