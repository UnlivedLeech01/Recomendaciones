package escom.ipn.SistemaMedico.auth.repository;

import escom.ipn.SistemaMedico.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Buscar un usuario por correo
    Optional<Usuario> findByCorreo(String correo);

    // Insertar una relación en la tabla "usuario_roles"
    @Modifying
    @Query(value = "INSERT INTO usuario_roles (usuario_id, rol_id) VALUES (?1, ?2)", nativeQuery = true)
    void insertUsuarioRol(Long usuarioId, Long rolId);

    // Opcional: Obtener usuarios por rol
    @Query(value = "SELECT u.* FROM usuarios u " +
                   "JOIN usuario_roles ur ON u.id = ur.usuario_id " +
                   "JOIN roles r ON ur.rol_id = r.id " +
                   "WHERE r.nombre = ?1", nativeQuery = true)
    List<Usuario> findUsuariosByRol(String rolNombre);
}