package escom.ipn.SistemaMedico.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escom.ipn.SistemaMedico.auth.model.Usuario;
import escom.ipn.SistemaMedico.auth.repository.RolRepository;
import escom.ipn.SistemaMedico.auth.repository.UsuarioRepository;

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
    
        Long rolId;
    
        // Asignar el ID según el rol recibido
        if ("ADMIN".equalsIgnoreCase(rolNombre)) {
            rolId = 1L;
        } else if ("ROLE_MEDICO".equalsIgnoreCase(rolNombre)) {
            rolId = 2L;
        } else if ("ROLE_PACIENTE".equalsIgnoreCase(rolNombre)) {
            rolId = 3L;
        } else {
            throw new IllegalArgumentException("El rol proporcionado no es válido: " + rolNombre);
        }
    
        // Encriptar la contraseña
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
    
        // Guardar el usuario en la tabla "usuarios"
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
    
        // Insertar la relación en la tabla "usuario_roles" usando el ID fijo del rol
        usuarioRepository.insertUsuarioRol(usuarioGuardado.getId(), rolId);
    
        return usuarioGuardado;
    }
    

    public boolean authenticate(String correo, String contrasena) {
        // Buscar al usuario por correo
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);

        // Verificar las credenciales
        return usuario.isPresent() && passwordEncoder.matches(contrasena, usuario.get().getContrasena());
    }
}