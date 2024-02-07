package io.github.jonasnascc.SeuSindico_API.entitiy.Chamado;

public enum StatusChamado {
    RESOLVIDO("resolvido"),
    PENDENTE("pendente"),
    VISUALIZADO("visualizado");

    public String status;

    StatusChamado(String status) {
        this.status = status;
    }
}
