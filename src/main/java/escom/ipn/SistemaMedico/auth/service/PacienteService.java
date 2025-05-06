package escom.ipn.SistemaMedico.auth.service;

import escom.ipn.SistemaMedico.auth.model.Paciente;
import escom.ipn.SistemaMedico.auth.model.Rol;
import escom.ipn.SistemaMedico.auth.repository.UsuarioRepository;
import escom.ipn.SistemaMedico.auth.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public boolean register(Paciente paciente) {
        // Validar si el correo ya está registrado
        if (usuarioRepository.findByCorreo(paciente.getCorreo()).isPresent()) {
            return false; // Paciente ya existe
        }

        // Buscar el rol "ROLE_PACIENTE"
        Optional<Rol> optionalRol = rolRepository.findByNombre("ROLE_PACIENTE");
        if (optionalRol.isEmpty()) {
            throw new RuntimeException("El rol ROLE_PACIENTE no existe en la base de datos.");
        }
        Rol rol = optionalRol.get();

        // Encriptar la contraseña
        paciente.setContrasena(passwordEncoder.encode(paciente.getContrasena()));

        // Guardar el paciente en la tabla "usuarios"
        Paciente pacienteGuardado = usuarioRepository.save(paciente);

        // Insertar la relación en la tabla "usuario_roles"
        usuarioRepository.insertUsuarioRol(pacienteGuardado.getId(), rol.getId());

        return true;
    }

    public boolean authenticate(String correo, String contrasena) {
        // Buscar al paciente por correo
        Optional<Paciente> paciente = usuarioRepository.findByCorreo(correo).map(user -> (Paciente) user);

        // Verificar las credenciales
        if (paciente.isPresent() && passwordEncoder.matches(contrasena, paciente.get().getContrasena())) {
            return true; // Credenciales válidas
        }
        return false; // Credenciales inválidas
    }
}