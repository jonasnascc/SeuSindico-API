package io.github.jonasnascc.SeuSindico_API.service.Contrato;

import io.github.jonasnascc.SeuSindico_API.dao.*;
import io.github.jonasnascc.SeuSindico_API.dto.Contrato.in.ContratoDTOIn;
import io.github.jonasnascc.SeuSindico_API.dto.Contrato.out.ContratoDTOOut;
import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Imovel;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Espaco;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Usuario;
import io.github.jonasnascc.SeuSindico_API.service.Boleto.BoletoService;
import io.github.jonasnascc.SeuSindico_API.service.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContratoService {
    private final ContratoRepository contratoRepository;

    private final UsuarioRepository usuarioRepository;

    private final ImovelRepository imovelRepository;

    private final EspacoRepository espacoRepository;

    private final BoletoService boletoService;

    private final BoletoRepository boletoRepository;

    public Long enviar(ContratoDTOIn dto, Long imovelId, Long espacoId, String cpfOcupante, String login) {
        Proprietario proprietario = this.validProprietario(login);
        Ocupante ocupante = this.validOcupante(cpfOcupante);
        Imovel imovel = this.validImovel(imovelId);
        Espaco espaco = validEspaco(espacoId);

        Optional<Espaco> optEspaco = imovel.getEspacos().stream()
                .filter(res -> res.getId().equals(espaco.getId())).findAny();

        if(optEspaco.isEmpty()) throw new RuntimeException("Espaço não pertence a esse imóvel.");

        this.checkContratoExiste(espacoId);

        Contrato contrato = DtoConverter.convertContratoDto(dto);
        contrato.setProprietario(proprietario);
        contrato.setOcupante(ocupante);

        Contrato savedContrato = contratoRepository.save(contrato);

        espaco.setContrato(savedContrato);

        savedContrato.setEspaco(espacoRepository.save(espaco));

        savedContrato = contratoRepository.save(savedContrato);
        boletoService.gerarBoletos(savedContrato);

        return savedContrato.getId();
    }

    public Long assinar(Long contratoId, String login) {
        Contrato contrato = validContrato(contratoId);
        Ocupante ocupante = validOcupante(login);

        if( !contrato.getOcupante().getId().equals(ocupante.getId()) ) {
            throw new RuntimeException("Contrato não encontrado.");
        }

        contrato.setAssinado();

        return contratoRepository.save(contrato).getId();
    }

    public Set<ContratoDTOOut> listar(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        Set<Contrato> contratos = usuario.getContratos();

        if(contratos == null) return Collections.emptySet();

        return contratos.stream().map(DtoConverter::convertContrato).collect(Collectors.toSet());
    }

    public void cancelar(Long contratoId, String login){
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        Contrato contrato = validContrato(contratoId);

        // Por enquanto, somente o proprietario poderá cancelar o contrato.
        if(!contrato.getProprietario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Contrato não encontrado.");
        }

        if(contrato.getBoletos() != null) boletoRepository.deleteAll(contrato.getBoletos());

        contratoRepository.delete(contrato);
    }

    private Imovel validImovel(Long imovelId) {
        return imovelRepository.findById(imovelId)
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado."));
    }

    private Espaco validEspaco(Long espacoId){
        return espacoRepository.findById(espacoId)
                .orElseThrow(() -> new RuntimeException("Espaco não encontrada."));
    }

    private Contrato validContrato(Long contratoId) {
        return contratoRepository.findById(contratoId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado."));
    }

    private Ocupante validOcupante(String ocupanteId) {
        return usuarioRepository.findOcupanteByLogin(ocupanteId)
                .orElseThrow(() -> new RuntimeException("Ocupante não encontrado."));
    }

    private Proprietario validProprietario(String proprietarioId) {
        return usuarioRepository.findProprietarioByLogin(proprietarioId)
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado."));
    }

    private void checkContratoExiste(Long espacoId) {
        Optional<Contrato> optContrato = contratoRepository.findByEspacoId(espacoId);
        if(optContrato.isPresent()) throw new RuntimeException("Contrato já existente.");
    }

}
