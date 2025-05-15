package escom.ipn.SistemaMedico.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "administradores") // Tabla específica de administradores
public class Administrador extends Usuario {

    @NotNull
    private String usuario; // Campo único para administrador

    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
