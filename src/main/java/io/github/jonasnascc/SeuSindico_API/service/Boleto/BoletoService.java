package io.github.jonasnascc.SeuSindico_API.service.Boleto;

import io.github.jonasnascc.SeuSindico_API.dao.BoletoRepository;
import io.github.jonasnascc.SeuSindico_API.dao.ContratoRepository;
import io.github.jonasnascc.SeuSindico_API.entitiy.Boleto.Boleto;
import io.github.jonasnascc.SeuSindico_API.entitiy.Boleto.StatusBoleto;
import io.github.jonasnascc.SeuSindico_API.entitiy.Contrato.Contrato;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoletoService {

    public final BoletoRepository boletoRepository;

    public final ContratoRepository contratoRepository;

    public void gerarBoletos(Contrato contrato) {
        Contrato savedContrato = contratoRepository.findById(contrato.getId())
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado."));

        int numParcelas = contrato.getNumeroParcelas();
        int intervalo = contrato.isPagamentoMensal() ? 30 : contrato.getIntervaloDias();

        LocalDate lastDate = LocalDate.now();
        Map<Integer, Boleto> boletosMap = new HashMap<>();
        for (int i = 1; i <= numParcelas; i++) {
            if(contrato.isPagamentoMensal()) {
                lastDate = lastDate.plusMonths(1);
            } else lastDate = lastDate.plusDays(intervalo);

            Boleto boleto = new Boleto();
            boleto.setPreco(contrato.getPreco());
            boleto.setParcelaNumero(i);
            boleto.setDataVencimento(lastDate);
            boleto.setContrato(savedContrato);
            boleto.setStatus(StatusBoleto.AGUARDANDO_PAGAMENTO);

            boletosMap.put(i, boletoRepository.save(boleto));
        }

        Double valorAdiantado = contrato.getValorAdiantado();

        for (int key = 1; key <= boletosMap.size(); key++) {
            if(valorAdiantado > 0) {
                Boleto bol = boletosMap.get(key);
                double resto = bol.getPreco() - valorAdiantado;

                if(resto > 0) {
                    valorAdiantado = 0.0;
                    bol.setPreco(resto);
                    boletoRepository.save(bol);
                }else if(resto < 0){
                    valorAdiantado = resto * (-1);
                    bol.setStatus(StatusBoleto.PAGO);
                    boletoRepository.save(bol);
                }
            }

        }

        contrato.setBoletos(Set.copyOf(boletosMap.values()));
    }
}
