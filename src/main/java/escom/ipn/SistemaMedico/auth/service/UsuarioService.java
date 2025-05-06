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
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Usuario register(Usuario usuario, String rolNombre) {
        // Validar si el correo ya está registrado
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }

        // Buscar el rol por nombre
        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RuntimeException("El rol " + rolNombre + " no existe en la base de datos."));

        // Encriptar la contraseña
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // Guardar el usuario en la tabla "usuarios"
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Insertar la relación en la tabla "usuario_roles"
        usuarioRepository.insertUsuarioRol(usuarioGuardado.getId(), rol.getId());

        return usuarioGuardado;
    }

    public boolean authenticate(String correo, String contrasena) {
        // Buscar al usuario por correo
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);

        // Verificar las credenciales
        return usuario.isPresent() && passwordEncoder.matches(contrasena, usuario.get().getContrasena());
    }
}