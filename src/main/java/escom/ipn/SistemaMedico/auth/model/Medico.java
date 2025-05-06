package escom.ipn.SistemaMedico.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "medicos") // Nombre de la tabla en la base de datos
public class Medico extends Usuario {

    @NotNull
    private String licencia; // Licencia médica única

    @NotNull
    private String especialidad; // Especialidad del médico

    // Getters y Setters
    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}