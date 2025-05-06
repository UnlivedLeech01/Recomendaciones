package escom.ipn.SistemaMedico.auth.repository;

import escom.ipn.SistemaMedico.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Buscar un usuario por correo
    Optional<Usuario> findByCorreo(String correo);

    // Insertar una relación en la tabla "usuario_roles", ignorando duplicados
    @Modifying
    @Query(value = "INSERT IGNORE INTO usuario_roles (usuario_id, rol_id) VALUES (?1, ?2)", nativeQuery = true)
    void insertUsuarioRol(Long usuarioId, Long rolId);

    // Eliminar una relación en la tabla "usuario_roles"
    @Modifying
    @Query(value = "DELETE FROM usuario_roles WHERE usuario_id = ?1 AND rol_id = ?2", nativeQuery = true)
    void deleteUsuarioRol(Long usuarioId, Long rolId);

    // Obtener usuarios por rol usando JPQL
    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.nombre = ?1")
    List<Usuario> findUsuariosByRol(String rolNombre);

    // Obtener usuarios registrados después de una fecha específica
    @Query("SELECT u FROM Usuario u WHERE u.fechaRegistro >= ?1")
    List<Usuario> findUsuariosByFechaRegistroAfter(LocalDateTime fecha);
}