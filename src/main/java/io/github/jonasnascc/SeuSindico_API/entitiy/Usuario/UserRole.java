package io.github.jonasnascc.SeuSindico_API.entitiy.Usuario;

public enum UserRole {
    PROPRIETARIO("proprietario"),
    OCUPANTE("ocupante");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
