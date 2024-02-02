package io.github.jonasnascc.SeuSindico_API.service.Imovel;

import io.github.jonasnascc.SeuSindico_API.dao.ImovelRepository;
import io.github.jonasnascc.SeuSindico_API.dao.UsuarioRepository;
import io.github.jonasnascc.SeuSindico_API.dto.Imovel.ImovelDTO;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Imovel;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImovelService {
    private final ImovelRepository imovelRepository;

    private final UsuarioRepository usuarioRepository;

    public Long save(ImovelDTO dto, String userId) {
        Proprietario proprietario = usuarioRepository.findProprietarioById(userId).orElseThrow(() -> new RuntimeException("Usuário inválido ou não encontrado."));

        Imovel imovel = new Imovel(
                dto.nome(),
                dto.decricao(),
                dto.quantidadeApartamentos(),
                dto.quantidadeAndares(),
                dto.apartamentosPorAndar()
        );

        imovel.setProprietario(proprietario);
        Imovel savedImovel = imovelRepository.save(imovel);

        proprietario.addImovel(savedImovel);
        usuarioRepository.save(proprietario);

        return savedImovel.getId();
    }
}
