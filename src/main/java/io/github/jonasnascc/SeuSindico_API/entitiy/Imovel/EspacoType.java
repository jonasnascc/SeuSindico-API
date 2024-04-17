package io.github.jonasnascc.SeuSindico_API.entitiy.Imovel;

public enum EspacoType {
    CASA("casa"), APARTAMENTO("apartamento"), ESCRITORIO("escritorio"), PONTO_COMERCIAL("ponto_comercial");

    private String tipo;

    EspacoType(String tipo){
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
