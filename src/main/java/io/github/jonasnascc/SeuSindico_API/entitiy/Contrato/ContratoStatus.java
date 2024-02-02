package io.github.jonasnascc.SeuSindico_API.entitiy.Contrato;

public enum ContratoStatus {
    PENDENTE("pendende"),
    ASSINADO("assinado");

    private String status;

    ContratoStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
