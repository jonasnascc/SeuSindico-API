package io.github.jonasnascc.SeuSindico_API.service.Contrato;

import io.github.jonasnascc.SeuSindico_API.dao.ContratoRepository;
import io.github.jonasnascc.SeuSindico_API.dao.ImovelRepository;
import io.github.jonasnascc.SeuSindico_API.dao.UsuarioRepository;
import io.github.jonasnascc.SeuSindico_API.dto.Contrato.ContratoDTO;
import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.ContratoStatus;
import io.github.jonasnascc.SeuSindico_API.entitiy.Imovel.Imovel;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Ocupante;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Proprietario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContratoService {
    private final ContratoRepository contratoRepository;

    private final UsuarioRepository usuarioRepository;

    private final ImovelRepository imovelRepository;

    public Long send(ContratoDTO dto, String proprietarioId, Long imovelId, String ocupanteId) {
        Proprietario proprietario = this.validProprietario(proprietarioId);
        Ocupante ocupante = this.validOcupante(ocupanteId);
        Imovel imovel = this.validImovel(imovelId);

        this.checkContratoExiste(imovel, proprietario, ocupante);

        Contrato contrato = this.convertDto(dto);
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

    public Contrato convertDto(ContratoDTO dto) {
        return new Contrato(
                dto.preco(),
                dto.observacoes(),
                dto.dataInicio(),
                dto.dataFim(),
                dto.intervaloDias(),
                dto.numeroParcelas(),
                ContratoStatus.PENDENTE
        );
    }
}
