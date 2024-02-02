package io.github.jonasnascc.SeuSindico_API.entitiy.Imovel;

public enum ImovelType {
    CASA("casa"), APARTAMENTO("apartamento"), ESCRITORIO("escritorio"), COMERCIO("comercio");

    private String tipo;

    ImovelType(String tipo){
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
