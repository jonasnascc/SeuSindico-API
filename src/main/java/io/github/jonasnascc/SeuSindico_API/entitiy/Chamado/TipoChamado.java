package io.github.jonasnascc.SeuSindico_API.entitiy.Chamado;

public enum TipoChamado {
    OCORRENCIA("ocorrencia"),
    RECLAMACAO("reclamacao"),
    SUGESTAO("sugestao");

    private String tipo;

    TipoChamado(String tipo) {
        this.tipo = tipo;
    }

}
