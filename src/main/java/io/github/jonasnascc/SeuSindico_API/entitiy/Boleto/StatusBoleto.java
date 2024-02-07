package io.github.jonasnascc.SeuSindico_API.entitiy.Boleto;

public enum StatusBoleto {
    AGUARDANDO_PAGAMENTO("aguardando_pagamento"),

    VENCIDO("vencido"),
    PAGO("pago");

    private String status;

    StatusBoleto(String status) {
        this.status = status;
    }

}
