package escom.ipn.SistemaMedico.auth.repository;

import escom.ipn.SistemaMedico.auth.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    // Buscar un rol por su nombre
    Optional<Rol> findByNombre(String nombre);
}