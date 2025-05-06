package escom.ipn.SistemaMedico.auth.service;

import escom.ipn.SistemaMedico.auth.model.Medico;
import escom.ipn.SistemaMedico.auth.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean register(Medico medico) {
        try {
            // Registrar al médico con el rol "ROLE_MEDICO"
            usuarioService.register(medico, "ROLE_MEDICO");
            return true;
        } catch (IllegalArgumentException e) {
            // Manejar el caso en que el correo ya esté registrado
            throw new IllegalArgumentException("Error al registrar el médico: " + e.getMessage());
        }
    }

    public boolean authenticate(String correo, String contrasena) {
        // Delegar la autenticación a UsuarioService
        return usuarioService.authenticate(correo, contrasena);
    }
}