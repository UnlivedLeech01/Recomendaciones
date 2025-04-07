package escom.ipn.SistemaMedico.auth.service;

import escom.ipn.SistemaMedico.auth.model.Usuario;
import escom.ipn.SistemaMedico.auth.model.Rol;
import escom.ipn.SistemaMedico.auth.repository.UsuarioRepository;
import escom.ipn.SistemaMedico.auth.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public boolean register(Usuario usuario, String rolNombre) {
        // Validar si el correo ya está registrado
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            return false; // Usuario ya existe
        }

        // Buscar el rol por nombre
        Optional<Rol> optionalRol = rolRepository.findByNombre(rolNombre);
        if (optionalRol.isEmpty()) {
            throw new RuntimeException("El rol " + rolNombre + " no existe en la base de datos.");
        }
        Rol rol = optionalRol.get();

        // Guardar el usuario en la tabla "usuarios"
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Insertar la relación en la tabla "usuario_roles"
        usuarioRepository.insertUsuarioRol(usuarioGuardado.getId(), rol.getId());

        return true;
    }

    public boolean authenticate(String correo, String contrasena) {
        // Buscar al usuario por correo
        Optional<Usuario> admin = usuarioRepository.findByCorreo(correo);

        // Verificar las credenciales
        if (admin.isPresent() && passwordEncoder.matches(contrasena, admin.get().getContrasena())) {
            return true; // Credenciales válidas
        }
        return false; // Credenciales inválidas
    }
}