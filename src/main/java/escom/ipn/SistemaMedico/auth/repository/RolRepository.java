package escom.ipn.SistemaMedico.auth.repository;

import escom.ipn.SistemaMedico.auth.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    // Buscar un rol por su nombre usando JPQL
    @Query("SELECT r FROM Rol r WHERE r.nombre = :nombre")
    Optional<Rol> findByNombre(@Param("nombre") String nombre);

    // Verificar si un rol existe por su nombre
    @Query("SELECT COUNT(r) > 0 FROM Rol r WHERE r.nombre = :nombre")
    boolean existsByNombre(@Param("nombre") String nombre);

    // Obtener todos los roles en orden alfabético
    List<Rol> findAllByOrderByNombreAsc();

    // Buscar roles que contengan una cadena en su nombre
    @Query("SELECT r FROM Rol r WHERE r.nombre LIKE %:nombre%")
    List<Rol> findRolesByNombreContaining(@Param("nombre") String nombre);
}