package io.github.jonasnascc.SeuSindico_API.entitiy.Usuario;

public enum UserType {
    PROPRIETARIO("proprietario"),
    OCUPANTE("ocupante");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
