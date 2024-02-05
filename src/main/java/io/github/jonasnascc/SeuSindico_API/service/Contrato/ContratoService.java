package io.github.jonasnascc.SeuSindico_API.service.Contrato;

import io.github.jonasnascc.SeuSindico_API.dao.ContratoRepository;
import io.github.jonasnascc.SeuSindico_API.dao.ImovelRepository;
import io.github.jonasnascc.SeuSindico_API.dao.UsuarioRepository;
import io.github.jonasnascc.SeuSindico_API.dto.Contrato.in.ContratoDTOIn;
import io.github.jonasnascc.SeuSindico_API.dto.Contrato.out.ContratoDTOOut;
import io.github.jonasnascc.SeuSindico_API.dto.Imovel.CasaDTO;
import io.github.jonasnascc.SeuSindico_API.dto.Usuario.UsuarioSimplesDTO;
import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.ContratoStatus;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Casa;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Imovel;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.ImovelType;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Usuario;
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

    public Long send(ContratoDTOIn dto, String proprietarioId, Long imovelId, String ocupanteId) {
        Proprietario proprietario = this.validProprietario(proprietarioId);
        Ocupante ocupante = this.validOcupante(ocupanteId);
        Imovel imovel = this.validImovel(imovelId);

        this.checkContratoExiste(imovel, proprietario, ocupante);

        Contrato contrato = DtoConverter.convertContratoDto(dto);
        contrato.setImovel(imovel);
        contrato.setProprietario(proprietario);
        contrato.setOcupante(ocupante);

        return contratoRepository.save(contrato).getId();
    }

    public Long sign(Long contratoId, String ocupanteId) {
        Contrato contrato = validContrato(contratoId);
        Ocupante ocupante = validOcupante(ocupanteId);

        if( !contrato.getOcupante().getId().equals(ocupante.getId()) ) {
            throw new RuntimeException("Contrato não encontrado.");
        }

        contrato.setAssinado();

        return contratoRepository.save(contrato).getId();
    }

    public Set<ContratoDTOOut> findContratos(String userId) {
        Usuario user = usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        Set<Contrato> contratos = user.getContratos();

        if(contratos == null) return Collections.emptySet();

        return contratos.stream().map(DtoConverter::convertContrato).collect(Collectors.toSet());
    }

    public void cancelar(Long contratoId, String proprietarioId){
        Contrato contrato = validContrato(contratoId);
        Proprietario proprietario = validProprietario(proprietarioId);

        if(!contrato.getProprietario().getId().equals(proprietario.getId())) {
            throw new RuntimeException("Contrato não encontrado.");
        }

        contratoRepository.delete(contrato);
    }

    private Imovel validImovel(Long imovelId) {
        return imovelRepository.findById(imovelId)
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado."));
    }

    private Contrato validContrato(Long contratoId) {
        return contratoRepository.findById(contratoId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));
    }

    private Ocupante validOcupante(String ocupanteId) {
        return usuarioRepository.findOcupanteById(ocupanteId)
                .orElseThrow(() -> new RuntimeException("Ocupante não encontrado."));
    }

    private Proprietario validProprietario(String proprietarioId) {
        return usuarioRepository.findProprietarioById(proprietarioId)
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado."));
    }

    private void checkContratoExiste(Imovel imovel, Proprietario proprietario, Ocupante ocupante) {
        Optional<Contrato> optContrato = contratoRepository.findContratoByImovelAndProprietarioAndOcupante(imovel, proprietario, ocupante);
        if(optContrato.isPresent()) throw new RuntimeException("Contrato já existe.");
    }

}
